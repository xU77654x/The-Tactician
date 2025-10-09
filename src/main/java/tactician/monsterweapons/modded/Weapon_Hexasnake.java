package tactician.monsterweapons.modded;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon5WindPower;
import tactician.powers.weapons.Weapon7ThunderPower;

public class Weapon_Hexasnake {
	@SpirePatch(cls = "spireMapOverhaul.zones.invasion.monsters.Hexasnake", method = "getMove", requiredModId = "anniv6")
	public static class Constrict {
		@SpireInsertPatch(rloc = 7)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon7ThunderPower(_inst))); }
		}
	}

	@SpirePatch(cls = "spireMapOverhaul.zones.invasion.monsters.Hexasnake", method = "getMove", requiredModId = "anniv6")
	public static class VenomousBite {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}
}