package tactician.patches.selfpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.character.TacticianRobin;
import tactician.monsterweapons.EnemyWeaponHelper;

import java.util.Objects;


public class FirstIntentWeaponPatch {
	@SpirePatch(clz = AbstractMonster.class, method = "createIntent")
	public static class pleaseWork {
		@SpireInsertPatch(loc = 482)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) {
				AbstractPower w = EnemyWeaponHelper.enemyWeaponCalc(_inst, _inst.intent);
				if (!Objects.equals(w.ID, StrengthPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(_inst, _inst, w)); }
			}
		}
	}
}