package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Mission;
import com.example.spacecolony.model.Threat;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.example.spacecolony.data.CrewDatabase;

public class BattleMenu extends AppCompatActivity{
    private TextView textBattleStatus;
    private Button buttonAttack;
    private Button buttonDefend;
    private Button buttonSkill;
    private TextView textStatus;
    private Button buttonAdjustTeamEnd;
    private Button buttonNextOrRetry;
    public static Mission currentMission;
    private boolean isPlayerWon = false;
    private boolean adjustedTeamReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_menu);

        textBattleStatus = findViewById(R.id.textBattleStatus);
        buttonAttack = findViewById(R.id.buttonAttack);
        buttonDefend = findViewById(R.id.buttonDefend);
        buttonSkill = findViewById(R.id.buttonSkill);
        textStatus = findViewById(R.id.textStatus);
        buttonAdjustTeamEnd = findViewById(R.id.buttonAdjustTeamEnd);
        buttonNextOrRetry = findViewById(R.id.buttonNextOrRetry);

        if (currentMission == null) {
            textBattleStatus.setText("No mission selected");
            buttonAttack.setEnabled(false);
            buttonDefend.setEnabled(false);
            buttonSkill.setEnabled(false);
            return;
        }

        buttonAdjustTeamEnd.setVisibility(View.GONE);
        buttonNextOrRetry.setVisibility(View.GONE);

        textBattleStatus.setText(
                currentMission.getMember1().getName() + " and " +
                currentMission.getMember2().getName() + " VS " +
                currentMission.getThreat().getName()
        );

        updateStatus();

        buttonAttack.setOnClickListener(v -> {
            handleAction("attack");
        });
        buttonDefend.setOnClickListener(v -> {
            handleAction("Defend");
        });
        buttonSkill.setOnClickListener(v -> {
            handleAction("Skill");
        });
        buttonAdjustTeamEnd.setOnClickListener(v -> {
            Intent intent = new Intent(BattleMenu.this, AdjustTeamMenu.class);
            adjustTeamLauncher.launch(intent);
        });
        buttonNextOrRetry.setOnClickListener(v -> {
            if (isPlayerWon) {
                Intent intent = new Intent(BattleMenu.this, MissionMenu.class);
                startActivity(intent);
                finish();
            } else {
                if (adjustedTeamReady) {
                    buttonAttack.setEnabled(true);
                    buttonDefend.setEnabled(true);
                    buttonSkill.setEnabled(true);
                    buttonAdjustTeamEnd.setVisibility(View.GONE);
                    buttonNextOrRetry.setVisibility(View.GONE);

                    textBattleStatus.setText(
                            currentMission.getMember1().getName() + " and " +
                            currentMission.getMember2().getName() + " VS " +
                            currentMission.getThreat().getName()
                    );
                    updateStatus();
                    adjustedTeamReady = false;
                } else {
                    textBattleStatus.setText("Please adjust your team first!");
                }
            }
        });
    }

    private void handleAction(String action) {
        if (currentMission == null) {
            return;
        }

        String battleLog = "";

        if (action.equals("attack")) {
            battleLog = currentMission.playerAttack();
        } else if (action.equals("Defend")) {
            battleLog = currentMission.playerDefend();
        } else if (action.equals("Skill")) {
            battleLog = currentMission.playerSkill();
        }

        if (currentMission.isThreatTurn() && !currentMission.getThreat().isDefeated() && !(currentMission.getMember1().isDefeated() && currentMission.getMember2().isDefeated())) {
            String threatLog = currentMission.threatTurn();
            battleLog += "\n" + threatLog;
        }

        textBattleStatus.setText(battleLog);
        updateStatus();

        if (currentMission.getThreat().isDefeated()) {
            isPlayerWon = true;
            textBattleStatus.setText(battleLog + "\nMission Completed!");
            showEndButtons(true);
            return;
        } else if (currentMission.getMember1().isDefeated() && currentMission.getMember2().isDefeated()) {
            currentMission.getMember1().die();
            currentMission.getMember2().die();
            isPlayerWon = false;
            textBattleStatus.setText(battleLog + "\nMission Failed!");
            showEndButtons(false);
            return;
        }
    }

    private final ActivityResultLauncher<Intent> adjustTeamLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() !=  null) {
            String crewName1 = result.getData().getStringExtra("crew1");
            String crewName2 = result.getData().getStringExtra("crew2");
            CrewDatabase crewDatabase = CrewDatabase.getInstance();
            CrewMember member1 = crewDatabase.getCrewMemberByName(crewName1);
            CrewMember member2 = crewDatabase.getCrewMemberByName(crewName2);

            boolean retrySuccess = currentMission.retryBattleWithNewTeam(member1, member2);

            if (retrySuccess) {
                adjustedTeamReady = true;
                isPlayerWon = false;
                textBattleStatus.setText("New Team Adjusted! Retry mission to continue our journey!");
                updateStatus();
            } else {
                textBattleStatus.setText("Failed to adjust team. Please try again.");
            }
        }
    });

    private void updateStatus() {
        if (currentMission == null) {
            return;
        }

        String status =
                currentMission.getMember1().getName() + " HP: " + currentMission.getMember1().getHealth() + "/" + currentMission.getMember1().getMaxHealth() + "\n" +
                currentMission.getMember2().getName() + " HP: " + currentMission.getMember2().getHealth() + "/" + currentMission.getMember2().getMaxHealth() + "\n" +
                currentMission.getThreat().getName() + " HP: " + currentMission.getThreat().getHealth();
        textStatus.setText(status);
    }

    private void showEndButtons(boolean won) {
        buttonAttack.setEnabled(false);
        buttonDefend.setEnabled(false);
        buttonSkill.setEnabled(false);
        buttonAdjustTeamEnd.setVisibility(View.VISIBLE);
        buttonNextOrRetry.setVisibility(View.VISIBLE);
        if (won) {
            buttonNextOrRetry.setText("Next Mission");
        } else {
            buttonNextOrRetry.setText("Retry Mission");
        }
    }
}
