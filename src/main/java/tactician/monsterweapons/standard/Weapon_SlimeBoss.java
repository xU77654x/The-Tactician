package tactician.monsterweapons.standard;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon7ThunderPower;

public class Weapon_SlimeBoss {
	@SpirePatch(clz = SlimeBoss.class, method = "takeTurn")
	public static class PrepareCrush {
		@SpireInsertPatch(rloc = 9)
		public static void Insert(SlimeBoss _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon7ThunderPower(_inst))); }
		}
	}
}