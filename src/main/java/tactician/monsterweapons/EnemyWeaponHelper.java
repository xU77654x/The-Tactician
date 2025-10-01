package tactician.monsterweapons;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.ending.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.powers.weapons.*;
import static com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.*;

public class EnemyWeaponHelper {
	public static AbstractPower enemyWeaponCalc (AbstractMonster m, AbstractMonster.Intent intent) {
		AbstractPower pow = new Weapon0NeutralPower(m);

		switch (m.id) {
			// Standard Act 1
			case AcidSlime_S.ID: case ApologySlime.ID:
				if (intent == DEBUFF) { pow = new Weapon3AxePower(m); }
				if (intent == ATTACK) { pow = new Weapon5WindPower(m); } break;
			case AcidSlime_M.ID: case AcidSlime_L.ID:
				if (intent == DEBUFF) { pow = new Weapon3AxePower(m); }
				if (intent == ATTACK) { pow = new Weapon5WindPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon8DarkPower(m); }
				if (intent == UNKNOWN) { pow = new StrengthPower(m, 999); } break;
			case SpikeSlime_S.ID: pow = new Weapon6FirePower(m); break;
			case SpikeSlime_M.ID: case SpikeSlime_L.ID:
				if (intent == DEBUFF) { pow = new Weapon2LancePower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon6FirePower(m); }
				if (intent == UNKNOWN) { pow = new StrengthPower(m, 999); } break;
			case Cultist.ID:
				if (intent == BUFF) { pow = new Weapon8DarkPower(m); }
				if (intent == ATTACK) { pow = new Weapon1SwordPower(m); } break;
			case JawWorm.ID:
				if (intent == ATTACK) { pow = new Weapon4BowPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon3AxePower(m); }
				if (intent == DEFEND_BUFF) { pow = new Weapon1SwordPower(m); } break;
			case LouseNormal.ID: // Red Louse
				if (intent == ATTACK) { pow = new Weapon7ThunderPower(m); }
				if (intent == BUFF) { pow = new Weapon7ThunderPower(m); } break;
			case LouseDefensive.ID: // Green Louse
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); }
				if (intent == DEBUFF) { pow = new Weapon2LancePower(m); } break;
			case FungiBeast.ID:
				if (intent == ATTACK) { pow = new Weapon8DarkPower(m); }
				if (intent == BUFF) { pow = new Weapon6FirePower(m); } break;
			case GremlinFat.ID: pow = new Weapon3AxePower(m); break;
			case GremlinWarrior.ID: pow = new Weapon8DarkPower(m); break; // Mad Gremlin
			case GremlinTsundere.ID: // Shield Gremlin
				if (intent == DEFEND) { pow = new Weapon6FirePower(m); }
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); } break;
			case GremlinThief.ID: pow = new Weapon5WindPower(m); break; // Sneaky Gremlin
			case GremlinWizard.ID:
				if (intent == UNKNOWN) { pow = new Weapon7ThunderPower(m); }
				if (intent == ATTACK) { pow = new Weapon7ThunderPower(m); } break;
			case Looter.ID: case Mugger.ID: case "downfall:LooterAlt": case "downfall:MuggerAlt":
				if (intent == ATTACK) {
					if (GameActionManager.turn == 1) { pow = new Weapon4BowPower(m); } // Uses Bow on Turn 1.
					else { pow = new Weapon1SwordPower(m); }} // Uses Sword after Turn 1. Does not function if enemy is Stunned or if Vault is used.
				if (intent == DEFEND) { pow = new Weapon5WindPower(m); }
				if (intent == ESCAPE) { pow = new Weapon5WindPower(m); } break;
			case SlaverBlue.ID:
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon2LancePower(m); } break;
			case SlaverRed.ID:
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon2LancePower(m); }
				if (intent == STRONG_DEBUFF) { pow = new Weapon2LancePower(m); } break;
			case GremlinNob.ID:
				if (intent == BUFF) { pow = new Weapon5WindPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon3AxePower(m); }
				if (intent == ATTACK) { pow = new Weapon1SwordPower(m); } break;
			case Sentry.ID:
				if (intent == ATTACK) { pow = new Weapon6FirePower(m); }
				if (intent == DEBUFF) { pow = new Weapon7ThunderPower(m); } break;
			case Lagavulin.ID:
				if (intent == SLEEP) { pow = new Weapon0NeutralPower(m); }
				if (intent == UNKNOWN) { pow = new Weapon0NeutralPower(m); }
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); }
				if (intent == STRONG_DEBUFF) { pow = new Weapon8DarkPower(m); } break;
			case SlimeBoss.ID:
				if (intent == STRONG_DEBUFF) { pow = new Weapon4BowPower(m); }
				if (intent == UNKNOWN) { pow = new StrengthPower(m, 999); }
				if (intent == ATTACK) { pow = new Weapon3AxePower(m); } break;
			case TheGuardian.ID:
				if (intent == STRONG_DEBUFF) { pow = new Weapon5WindPower(m); }
				if (intent == DEFEND) { pow = new Weapon7ThunderPower(m); }
				if (intent == ATTACK_BUFF) { pow = new Weapon3AxePower(m); }
				if (intent == BUFF) { pow = new Weapon0NeutralPower(m); }
				if (intent == ATTACK) { pow = new StrengthPower(m, 999); } break; // Guardian has 3 different attacks.
			case Hexaghost.ID:
				if (intent == UNKNOWN) { pow = new Weapon8DarkPower(m); }
				else { pow = new StrengthPower(m, 999); } break; // Hexaghost's patch contains all assignments beyond Turn 1.

			// Standard Act 2
			case Byrd.ID:
				if (intent == ATTACK) {
					if (GameActionManager.turn == 1) { pow = new Weapon0NeutralPower(m); }
					else { pow = new StrengthPower(m, 999); }
				} // Byrd can only use Peck on Turn 1.
				if (intent == BUFF) { pow = new Weapon6FirePower(m); }
				if (intent == UNKNOWN) { pow = new Weapon6FirePower(m); }
				if (intent == STUN) { pow = new Weapon0NeutralPower(m); } break;
			case Chosen.ID:
				if (intent == ATTACK) {
					if (GameActionManager.turn == 1) { pow = new Weapon2LancePower(m); }
					else { pow = new StrengthPower(m, 999); }
				}  // Chosen can only use Poke on Turn 1.
				if (intent == ATTACK_DEBUFF) { pow = new Weapon5WindPower(m); }
				if (intent == DEBUFF) { pow = new Weapon4BowPower(m); }
				if (intent == STRONG_DEBUFF) { pow = new Weapon8DarkPower(m); } break;
			case Centurion.ID:
				if (intent == ATTACK) { pow = new Weapon3AxePower(m); } // Both attacks are Axe.
				if (intent == DEFEND) { pow = new Weapon8DarkPower(m); } break;
			case Healer.ID:
				if (intent == BUFF) {
					if (GameActionManager.turn == 1) { pow = new Weapon6FirePower(m); }
					else { pow = new StrengthPower(m, 999); }
				} // Mystic can only use StrengthUp on Turn 1.
				if (intent == ATTACK_DEBUFF) { pow = new Weapon7ThunderPower(m); } break;
			case SphericGuardian.ID:
				if (intent == ATTACK) { pow = new Weapon7ThunderPower(m); }
				if (intent == DEFEND) { pow = new Weapon7ThunderPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon5WindPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon6FirePower(m); } break;
			case ShelledParasite.ID: case "beaked:SuperParasite":
				if (intent == ATTACK) { pow = new Weapon3AxePower(m); }
				if (intent == ATTACK_BUFF) { pow = new Weapon6FirePower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon2LancePower(m); } break;
			case SnakePlant.ID:
				if (intent == ATTACK) { pow = new Weapon4BowPower(m); }
				if (intent == STRONG_DEBUFF) { pow = new Weapon5WindPower(m); } break;
			case Snecko.ID:
				if (intent == STRONG_DEBUFF) { pow = new Weapon2LancePower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon3AxePower(m); }
				if (intent == ATTACK) { pow = new Weapon8DarkPower(m); } break;
			case GremlinLeader.ID:
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); }
				if (intent == UNKNOWN) { pow = new Weapon1SwordPower(m); }
				if (intent == DEFEND_BUFF) { pow = new Weapon1SwordPower(m); } break;
			case Taskmaster.ID: pow = new Weapon4BowPower(m); break;
			case BookOfStabbing.ID: pow = new Weapon1SwordPower(m); break; // Both attacks are Sword.
			case BronzeAutomaton.ID:
				if (intent == UNKNOWN) { pow = new Weapon4BowPower(m); }
				if (intent == DEFEND_BUFF) { pow = new Weapon7ThunderPower(m); }
				if (intent == STUN) { pow = new Weapon0NeutralPower(m); }
				if (intent == ATTACK) { pow = new StrengthPower(m, 999); } break;
			case BronzeOrb.ID: case "BronzeOrbWhoReallyLikesDefectForSomeReason":
				if (intent == STRONG_DEBUFF) { pow = new Weapon8DarkPower(m); }
				if (intent == ATTACK) { pow = new Weapon6FirePower(m); }
				if (intent == DEFEND) { pow = new Weapon3AxePower(m); } break;
			case Champ.ID:
				if (intent == DEFEND_BUFF) { pow = new Weapon2LancePower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon4BowPower(m); }
				if (intent == DEBUFF) { pow = new Weapon4BowPower(m); }
				if (intent == ATTACK) { pow = new Weapon1SwordPower(m); } // Both attacks are Sword.
				if (intent == BUFF) { pow = new Weapon5WindPower(m); } break; // Both buffs are Wind.
			case TheCollector.ID:
				if (intent == UNKNOWN) { pow = new Weapon8DarkPower(m); }
				if (intent == DEFEND_BUFF) { pow = new Weapon5WindPower(m); }
				if (intent == ATTACK) { pow = new Weapon6FirePower(m); }
				if (intent == STRONG_DEBUFF) { pow = new Weapon8DarkPower(m); } break;
			case TorchHead.ID: pow = new Weapon3AxePower(m); break;
			case BanditPointy.ID: pow = new Weapon1SwordPower(m); break;
			case BanditLeader.ID: // Romeo.
				if (intent == UNKNOWN) { pow = new Weapon7ThunderPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon4BowPower(m); }
				if (intent == ATTACK) { pow = new Weapon5WindPower(m); } break;
			case BanditBear.ID:
				if (intent == STRONG_DEBUFF) { pow = new Weapon8DarkPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon2LancePower(m); }
				if (intent == ATTACK) { pow = new Weapon3AxePower(m); } break;

			// Standard Act 3
			case Maw.ID:
				if (intent == STRONG_DEBUFF) { pow = new Weapon4BowPower(m); }
				if (intent == BUFF) { pow = new Weapon2LancePower(m); }
				if (intent == ATTACK) { pow = new Weapon3AxePower(m); } break;
			case OrbWalker.ID:
				if (intent == ATTACK_DEBUFF) { pow = new Weapon6FirePower(m); }
				if (intent == ATTACK) { pow = new Weapon3AxePower(m); } break;
			case Darkling.ID:
				if (intent == ATTACK) {
					if (GameActionManager.turn == 1) { pow = new Weapon2LancePower(m); }
					else { pow = new StrengthPower(m, 999); }
				} // Darkling can only use Nip on Turn 1.
				if (intent == DEFEND || intent == DEFEND_BUFF) { pow = new Weapon7ThunderPower(m); }
				if (intent == BUFF || intent == UNKNOWN) { pow = new Weapon0NeutralPower(m); } break;
			case Transient.ID: pow = new Weapon8DarkPower(m); break;
			case SpireGrowth.ID:
				if (intent == STRONG_DEBUFF) { pow = new Weapon5WindPower(m); }
				if (intent == ATTACK) { pow = new Weapon1SwordPower(m); } break; // 2 attacks
			case WrithingMass.ID:
				if (intent == STRONG_DEBUFF) { pow = new Weapon4BowPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon1SwordPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon5WindPower(m); }
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); } break;
			case Spiker.ID:
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); }
				if (intent == BUFF) { pow = new Weapon2LancePower(m); } break;
			case Exploder.ID:
				if (intent == ATTACK) { pow = new Weapon6FirePower(m); }
				if (intent == UNKNOWN) { pow = new Weapon6FirePower(m); } break;
			case Repulsor.ID:
				if (intent == ATTACK) { pow = new Weapon7ThunderPower(m); }
				if (intent == DEBUFF) { pow = new Weapon7ThunderPower(m); } break;
			case GiantHead.ID:
				if (intent == ATTACK) { pow = new Weapon8DarkPower(m); }
				if (intent == DEBUFF) { pow = new Weapon3AxePower(m); } break;
			case Nemesis.ID:
				if (intent == ATTACK) { pow = new Weapon1SwordPower(m); }
				if (intent == DEBUFF) { pow = new Weapon6FirePower(m); } break;
			case Reptomancer.ID:
				if (intent == UNKNOWN) { pow = new Weapon6FirePower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon7ThunderPower(m); }
				if (intent == ATTACK) { pow = new Weapon5WindPower(m); } break;
			case SnakeDagger.ID: case "downfall:DoomedDagger":
				if (intent == ATTACK_DEBUFF) { pow = new Weapon1SwordPower(m); }
				if (intent == ATTACK) { pow = new Weapon3AxePower(m); } break;
			case AwakenedOne.ID:
				if (intent == ATTACK) {
					if (GameActionManager.turn == 1) { pow = new Weapon1SwordPower(m); }
					else { pow = new StrengthPower(m, 999); }
				} // Awakened One can only use Slash on Turn 1.
				if (intent == UNKNOWN) { pow = new Weapon0NeutralPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon4BowPower(m); } break;
			case TimeEater.ID:
				if (intent == DEFEND_DEBUFF) { pow = new Weapon7ThunderPower(m); }
				if (intent == BUFF) { pow = new Weapon8DarkPower(m); }
				if (intent == ATTACK) { pow = new Weapon5WindPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon2LancePower(m); }break;
			case Deca.ID:
				if (intent == DEFEND) { pow = new Weapon8DarkPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon4BowPower(m); } break;
			case Donu.ID:
				if (intent == BUFF) { pow = new Weapon4BowPower(m); }
				if (intent == ATTACK) { pow = new Weapon8DarkPower(m); } break;

			// Standard Act 4
			case SpireSpear.ID:
				if (intent == ATTACK_DEBUFF) { pow = new Weapon6FirePower(m); }
				if (intent == BUFF) { pow = new Weapon1SwordPower(m); }
				if (intent == ATTACK) { pow = new Weapon2LancePower(m); } break;
			case SpireShield.ID:
				if (intent == ATTACK_DEBUFF) { pow = new Weapon5WindPower(m); }
				if (intent == DEFEND) { pow = new Weapon7ThunderPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon3AxePower(m); } break;
			case CorruptHeart.ID:
				if (intent == STRONG_DEBUFF) { pow = new Weapon0NeutralPower(m); }
				if (intent == BUFF) { pow = new Weapon0NeutralPower(m); }
				if (intent == ATTACK) { pow = new StrengthPower(m, 999); } break;


			// Downfall Events and Boss Minions
			case "downfall:Augmenter":
				if (intent == ATTACK_BUFF) { pow = new Weapon6FirePower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon8DarkPower(m); } break;
			case "downfall:FaceTrader":
				if (intent == ATTACK_DEBUFF) { pow = new Weapon0NeutralPower(m); } // Three possible attacks on turn 1. :(
				if (intent == DEFEND_BUFF) { pow = new StrengthPower(m, 999); } break; // Requires a patch.
			case "downfall:LadyInBlue":
				if (intent == ATTACK_BUFF) { pow = new Weapon6FirePower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon8DarkPower(m); }
				if (intent == BUFF) { pow = new Weapon7ThunderPower(m); }
				if (intent == STRONG_DEBUFF) { pow = new Weapon5WindPower(m); } break;
			case "downfall:GrowingTotem": pow = new Weapon1SwordPower(m); break;
			case "downfall:ForgetfulTotem": pow = new Weapon2LancePower(m); break;
			case "downfall:ChangingTotem": pow = new Weapon3AxePower(m); break;
			case "downfall:FleeingMerchant":
				if (intent == DEFEND) { pow = new Weapon4BowPower(m); }
				if (intent == ATTACK) { pow = new Weapon7ThunderPower(m); }
				if (intent == BUFF) { pow = new Weapon8DarkPower(m); }
				if (intent == ESCAPE) { pow = new Weapon0NeutralPower(m); } break;
			case "downfall:MushroomRed": pow = new Weapon2LancePower(m); break;
			case "downfall:MushroomWhite": pow = new Weapon5WindPower(m); break;
			case "downfall:MushroomPurple": pow = new Weapon8DarkPower(m); break;
			case "downfall:Fortification": pow = new Weapon0NeutralPower(m); break;
			case "downfall:FuzzyLouseTangerine":
				if (intent == SLEEP) { pow = new Weapon0NeutralPower(m); }
				if (intent == ATTACK) { pow = new Weapon7ThunderPower(m); }
				if (intent == BUFF) { pow = new Weapon7ThunderPower(m); } break;

			// Downfall Act 4
			case "downfall:GauntletIronclad":
				if (intent == ATTACK) { pow = new Weapon1SwordPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon1SwordPower(m); }
				if (intent == ATTACK_DEBUFF) { pow = new Weapon3AxePower(m); }
				if (intent == DEFEND) { pow = new Weapon6FirePower(m); }
				if (intent == BUFF) { pow = new Weapon6FirePower(m); } break;
			case "downfall:GauntletSilent":
				if (intent == ATTACK) { pow = new Weapon5WindPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon5WindPower(m); }
				if (intent == DEFEND_DEBUFF) { pow = new Weapon2LancePower(m); }
				if (intent == DEFEND) { pow = new Weapon8DarkPower(m); }
				if (intent == BUFF) { pow = new Weapon8DarkPower(m); } break;
			case "downfall:GauntletDefect":
				if (intent == ATTACK) { pow = new Weapon7ThunderPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon7ThunderPower(m); }
				if (intent == DEFEND) { pow = new Weapon4BowPower(m); }
				if (intent == BUFF) { pow = new Weapon3AxePower(m); } break;
			case "downfall:GauntletWatcher":
				if (intent == ATTACK) { pow = new Weapon1SwordPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon2LancePower(m); }
				if (intent == DEFEND) { pow = new Weapon7ThunderPower(m); }
				if (intent == BUFF) { pow = new Weapon6FirePower(m); } break;
			case "downfall:GauntletHermit":
				if (intent == ATTACK) { pow = new Weapon4BowPower(m); }
				if (intent == ATTACK_DEFEND) { pow = new Weapon4BowPower(m); }
				if (intent == DEFEND) { pow = new Weapon8DarkPower(m); }
				if (intent == BUFF) { pow = new Weapon5WindPower(m); } break;
			case "downfall:NeowBoss": pow = new StrengthPower(m, 999); break;
			case "downfall:NeowBossFinal": case "downfall:Neow4Life30Heal":
				if (intent == ATTACK) { pow = new Weapon0NeutralPower(m); } // Neow's attacks are Neutral, unlike the Heart.
				if (intent == STRONG_DEBUFF) { pow = new Weapon8DarkPower(m); }
				if (intent == BUFF) { pow = new Weapon4BowPower(m); } break;

			// Downfall Bosses (Note: intentions cannot be read from cards on turn start)
			case "downfall:Ironclad":
				switch (AbstractDungeon.actNum) {
					case 1: pow = new Weapon1SwordPower(m); break;
					case 2: pow = new Weapon3AxePower(m); break;
					case 3: pow = new Weapon6FirePower(m); break;
				} break;
			case "downfall:Silent": case "downfall:MirrorImageSilent":
				switch (AbstractDungeon.actNum) {
					case 1: pow = new Weapon5WindPower(m); break;
					case 2: if (GameActionManager.turn == 1) { pow = new Weapon8DarkPower(m); }
							else { pow = new StrengthPower(m, 999); } break;
					case 3: pow = new Weapon2LancePower(m); break;
				} break;
			case "downfall:Defect":
				switch (AbstractDungeon.actNum) {
					case 1: pow = new Weapon7ThunderPower(m); break;
					case 2: pow = new Weapon4BowPower(m); break;
					case 3: pow = new Weapon3AxePower(m); break;
				} break;
			case "downfall:Watcher":
				switch (AbstractDungeon.actNum) {
					case 1: pow = new Weapon2LancePower(m); break;
					case 2: pow = new Weapon6FirePower(m); break;
					case 3: pow = new Weapon1SwordPower(m); break;
				} break;
			case "downfall:Hermit":
				switch (AbstractDungeon.actNum) {
					case 1: pow = new Weapon4BowPower(m); break;
					case 2: pow = new Weapon5WindPower(m); break;
					case 3: pow = new Weapon8DarkPower(m); break;
				} break;
			case "downfall:CharBossMerchant": pow = new Weapon7ThunderPower(m); break;


			// Jungle Act 2


			// Other Modded Enemies

		}
		return pow;
	}
}