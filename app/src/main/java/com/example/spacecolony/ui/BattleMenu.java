package com.example.spacecolony.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Mission;
import com.example.spacecolony.model.Threat;

public class BattleMenu extends AppCompatActivity{
    private TextView textBattleStatus;
    private Button buttonAttack;
    private Button buttonDefend;
    private Button buttonSkill;
    private TextView textStatus;
    public static Mission currentMission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_menu);

        textBattleStatus = findViewById(R.id.textBattleStatus);
        buttonAttack = findViewById(R.id.buttonAttack);
        buttonDefend = findViewById(R.id.buttonDefend);
        buttonSkill = findViewById(R.id.buttonSkill);
        textStatus = findViewById(R.id.textStatus);

        if (currentMission == null) {
            textBattleStatus.setText("No mission selected");
            buttonAttack.setEnabled(false);
            buttonDefend.setEnabled(false);
            buttonSkill.setEnabled(false);
            return;
        }
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

        if (currentMission.getThreat().isDefeated() || (currentMission.getMember1().isDefeated() && currentMission.getMember2().isDefeated())) {
            buttonAttack.setEnabled(false);
            buttonDefend.setEnabled(false);
            buttonSkill.setEnabled(false);
        }
    }

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
}
