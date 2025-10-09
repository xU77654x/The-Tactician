package tactician.monsterweapons.modded;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon3AxePower;
import tactician.powers.weapons.Weapon4BowPower;
import tactician.powers.weapons.Weapon7ThunderPower;

public class Weapon_WarGolem {
	@SpirePatch(cls = "spireMapOverhaul.zones.invasion.monsters.WarGolem", method = "getMove", requiredModId = "anniv6")
	public static class Heartseeker {
		@SpireInsertPatch(rloc = 1)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon7ThunderPower(_inst))); }
		}
	}

	@SpirePatch(cls = "spireMapOverhaul.zones.invasion.monsters.WarGolem", method = "getMove", requiredModId = "anniv6")
	public static class Shieldbreaker {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon3AxePower(_inst))); }
		}
	}

	@SpirePatch(cls = "spireMapOverhaul.zones.invasion.monsters.WarGolem", method = "getMove", requiredModId = "anniv6")
	public static class Legbuster {
		@SpireInsertPatch(rloc = 6)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon4BowPower(_inst))); }
		}
	}
}