package tactician.monsterweapons.modded;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon5WindPower;
import tactician.powers.weapons.Weapon6FirePower;

public class Weapon_NeowBossFinal {
	@SpirePatch(cls = "downfall.monsters.NeowBossFinal", method = "getMove", requiredModId = "downfall")
	public static class MultiHit {
		@SpireInsertPatch(rloc = 10)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon5WindPower(_inst))); }
		}
	}

	@SpirePatch(cls = "downfall.monsters.NeowBossFinal", method = "getMove", requiredModId = "downfall")
	public static class SingleHit {
		@SpireInsertPatch(rloc = 6)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon6FirePower(_inst))); }
		}
	}
}