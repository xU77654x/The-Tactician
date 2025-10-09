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
	public static AbstractPower enemyWeaponCalc (AbstractMonster m, AbstractMonster.Intent i) {
		AbstractPower w = new Weapon0NeutralPower(m);

		switch (m.id) {
			// Standard Act 1
			case AcidSlime_S.ID: case ApologySlime.ID:
				if (i == DEBUFF) { w = new Weapon3AxePower(m); }
				if (i == ATTACK) { w = new Weapon5WindPower(m); } break;
			case AcidSlime_M.ID: case AcidSlime_L.ID:
				if (i == DEBUFF) { w = new Weapon3AxePower(m); }
				if (i == ATTACK) { w = new Weapon5WindPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon8DarkPower(m); }
				if (i == UNKNOWN) { w = new StrengthPower(m, 999); } break;
			case SpikeSlime_S.ID: w = new Weapon6FirePower(m); break;
			case SpikeSlime_M.ID: case SpikeSlime_L.ID:
				if (i == DEBUFF) { w = new Weapon2LancePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon6FirePower(m); }
				if (i == UNKNOWN) { w = new StrengthPower(m, 999); } break;
			case Cultist.ID:
				if (i == BUFF) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK) { w = new Weapon1SwordPower(m); } break;
			case JawWorm.ID:
				if (i == ATTACK) { w = new Weapon4BowPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon3AxePower(m); }
				if (i == DEFEND_BUFF) { w = new Weapon1SwordPower(m); } break;
			case LouseNormal.ID: // Red Louse
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == BUFF) { w = new Weapon7ThunderPower(m); } break;
			case LouseDefensive.ID: // Green Louse
				if (i == ATTACK) { w = new Weapon2LancePower(m); }
				if (i == DEBUFF) { w = new Weapon2LancePower(m); } break;
			case FungiBeast.ID:
				if (i == ATTACK) { w = new Weapon8DarkPower(m); }
				if (i == BUFF) { w = new Weapon6FirePower(m); } break;
			case GremlinFat.ID: w = new Weapon3AxePower(m); break;
			case GremlinWarrior.ID: w = new Weapon8DarkPower(m); break; // Mad Gremlin
			case GremlinTsundere.ID: case "anniv6:GremlindBodyGuard": // Shield Gremlin
				if (i == DEFEND) { w = new Weapon6FirePower(m); }
				if (i == ATTACK) { w = new Weapon2LancePower(m); }
				if (i == ATTACK_BUFF ) { w = new Weapon2LancePower(m); } break;
			case GremlinThief.ID: w = new Weapon5WindPower(m); break; // Sneaky Gremlin
			case GremlinWizard.ID:
				if (i == UNKNOWN) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); } break;
			case Looter.ID: case Mugger.ID: case "downfall:LooterAlt": case "downfall:MuggerAlt":
				if (i == ATTACK) {
					if (GameActionManager.turn == 1) { w = new Weapon4BowPower(m); } // Uses Bow on Turn 1.
					else { w = new Weapon1SwordPower(m); }} // Uses Sword after Turn 1. Does not function if enemy is Stunned or if Vault is used.
				if (i == DEFEND) { w = new Weapon5WindPower(m); }
				if (i == ESCAPE) { w = new Weapon5WindPower(m); } break;
			case SlaverBlue.ID:
				if (i == ATTACK) { w = new Weapon2LancePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon2LancePower(m); } break;
			case SlaverRed.ID:
				if (i == ATTACK) { w = new Weapon2LancePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon2LancePower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon2LancePower(m); } break;
			case GremlinNob.ID:
				if (i == BUFF) { w = new Weapon5WindPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon3AxePower(m); }
				if (i == ATTACK) { w = new Weapon1SwordPower(m); } break;
			case Sentry.ID:
				if (i == ATTACK) { w = new Weapon6FirePower(m); }
				if (i == DEBUFF) { w = new Weapon7ThunderPower(m); } break;
			case Lagavulin.ID:
				if (i == SLEEP) { w = new Weapon0NeutralPower(m); }
				if (i == UNKNOWN) { w = new Weapon0NeutralPower(m); }
				if (i == ATTACK) { w = new Weapon2LancePower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon8DarkPower(m); } break;
			case SlimeBoss.ID:
				if (i == STRONG_DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == UNKNOWN) { w = new StrengthPower(m, 999); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); } break;
			case TheGuardian.ID:
				if (i == STRONG_DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == DEFEND) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_BUFF) { w = new Weapon3AxePower(m); }
				if (i == BUFF) { w = new Weapon0NeutralPower(m); }
				if (i == ATTACK) { w = new StrengthPower(m, 999); } break; // Guardian has 3 different attacks.
			case Hexaghost.ID:
				if (i == UNKNOWN) { w = new Weapon8DarkPower(m); }
				else { w = new StrengthPower(m, 999); } break; // Hexaghost's patch contains all assignments beyond Turn 1.

			// Standard Act 2
			case Byrd.ID:
				if (i == ATTACK) {
					if (GameActionManager.turn == 1) { w = new Weapon0NeutralPower(m); }
					else { w = new StrengthPower(m, 999); }
				} // Byrd can only use Peck on Turn 1.
				if (i == BUFF) { w = new Weapon6FirePower(m); }
				if (i == UNKNOWN) { w = new Weapon6FirePower(m); }
				if (i == STUN) { w = new Weapon0NeutralPower(m); } break;
			case Chosen.ID:
				if (i == ATTACK) {
					if (GameActionManager.turn == 1) { w = new Weapon2LancePower(m); }
					else { w = new StrengthPower(m, 999); }
				}  // Chosen can only use Poke on Turn 1.
				if (i == ATTACK_DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon8DarkPower(m); } break;
			case Centurion.ID:
				if (i == ATTACK) { w = new Weapon3AxePower(m); } // Both attacks are Axe.
				if (i == DEFEND) { w = new Weapon8DarkPower(m); } break;
			case Healer.ID:
				if (i == BUFF) {
					if (GameActionManager.turn == 1) { w = new Weapon6FirePower(m); }
					else { w = new StrengthPower(m, 999); }
				} // Mystic can only use StrengthUp on Turn 1.
				if (i == ATTACK_DEBUFF) { w = new Weapon7ThunderPower(m); } break;
			case SphericGuardian.ID:
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == DEFEND) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon5WindPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon6FirePower(m); } break;
			case ShelledParasite.ID: case "beaked:SuperParasite":
				if (i == ATTACK) { w = new Weapon3AxePower(m); }
				if (i == ATTACK_BUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon2LancePower(m); } break;
			case SnakePlant.ID:
				if (i == ATTACK) { w = new Weapon4BowPower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon5WindPower(m); } break;
			case Snecko.ID:
				if (i == STRONG_DEBUFF) { w = new Weapon2LancePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon3AxePower(m); }
				if (i == ATTACK) { w = new Weapon8DarkPower(m); } break;
			case GremlinLeader.ID:
				if (i == ATTACK) { w = new Weapon2LancePower(m); }
				if (i == UNKNOWN) { w = new Weapon1SwordPower(m); }
				if (i == DEFEND_BUFF) { w = new Weapon1SwordPower(m); } break;
			case Taskmaster.ID: w = new Weapon4BowPower(m); break;
			case BookOfStabbing.ID: w = new Weapon1SwordPower(m); break; // Both attacks are Sword.
			case BronzeAutomaton.ID:
				if (i == UNKNOWN) { w = new Weapon4BowPower(m); }
				if (i == DEFEND_BUFF) { w = new Weapon7ThunderPower(m); }
				if (i == STUN) { w = new Weapon0NeutralPower(m); }
				if (i == ATTACK) { w = new StrengthPower(m, 999); } break;
			case BronzeOrb.ID: case "BronzeOrbWhoReallyLikesDefectForSomeReason":
				if (i == STRONG_DEBUFF) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK) { w = new Weapon6FirePower(m); }
				if (i == DEFEND) { w = new Weapon3AxePower(m); } break;
			case Champ.ID:
				if (i == DEFEND_BUFF) { w = new Weapon2LancePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == ATTACK) { w = new Weapon1SwordPower(m); } // Both attacks are Sword.
				if (i == BUFF) { w = new Weapon5WindPower(m); } break; // Both buffs are Wind.
			case TheCollector.ID:
				if (i == UNKNOWN) { w = new Weapon8DarkPower(m); }
				if (i == DEFEND_BUFF) { w = new Weapon5WindPower(m); }
				if (i == ATTACK) { w = new Weapon6FirePower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon8DarkPower(m); } break;
			case TorchHead.ID: w = new Weapon3AxePower(m); break;
			case BanditPointy.ID: w = new Weapon1SwordPower(m); break;
			case BanditLeader.ID: // Romeo.
				if (i == UNKNOWN) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == ATTACK) { w = new Weapon5WindPower(m); } break;
			case BanditBear.ID:
				if (i == STRONG_DEBUFF) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon2LancePower(m); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); } break;

			// Standard Act 3
			case Maw.ID:
				if (i == STRONG_DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == BUFF) { w = new Weapon2LancePower(m); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); } break;
			case OrbWalker.ID:
				if (i == ATTACK_DEBUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); } break;
			case Darkling.ID:
				if (i == ATTACK) {
					if (GameActionManager.turn == 1) { w = new Weapon2LancePower(m); }
					else { w = new StrengthPower(m, 999); }
				} // Darkling can only use Nip on Turn 1.
				if (i == DEFEND || i == DEFEND_BUFF) { w = new Weapon7ThunderPower(m); }
				if (i == BUFF || i == UNKNOWN) { w = new Weapon0NeutralPower(m); } break;
			case Transient.ID: w = new Weapon8DarkPower(m); break;
			case SpireGrowth.ID:
				if (i == STRONG_DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == ATTACK) { w = new Weapon1SwordPower(m); } break; // 2 attacks
			case WrithingMass.ID:
				if (i == STRONG_DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == ATTACK) { w = new Weapon2LancePower(m); } break;
			case Spiker.ID:
				if (i == ATTACK) { w = new Weapon2LancePower(m); }
				if (i == BUFF) { w = new Weapon2LancePower(m); } break;
			case Exploder.ID:
				if (i == ATTACK) { w = new Weapon6FirePower(m); }
				if (i == UNKNOWN) { w = new Weapon6FirePower(m); } break;
			case Repulsor.ID:
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == DEBUFF) { w = new Weapon7ThunderPower(m); } break;
			case GiantHead.ID:
				if (i == ATTACK) { w = new Weapon8DarkPower(m); }
				if (i == DEBUFF) { w = new Weapon3AxePower(m); } break;
			case Nemesis.ID:
				if (i == ATTACK) { w = new Weapon1SwordPower(m); }
				if (i == DEBUFF) { w = new Weapon6FirePower(m); } break;
			case Reptomancer.ID:
				if (i == UNKNOWN) { w = new Weapon6FirePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK) { w = new Weapon5WindPower(m); } break;
			case SnakeDagger.ID: case "downfall:DoomedDagger":
				if (i == ATTACK_DEBUFF) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); } break;
			case AwakenedOne.ID:
				if (i == ATTACK) {
					if (GameActionManager.turn == 1) { w = new Weapon1SwordPower(m); }
					else { w = new StrengthPower(m, 999); }
				} // Awakened One can only use Slash on Turn 1.
				if (i == UNKNOWN) { w = new Weapon0NeutralPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon4BowPower(m); } break;
			case TimeEater.ID:
				if (i == DEFEND_DEBUFF) { w = new Weapon7ThunderPower(m); }
				if (i == BUFF) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK) { w = new Weapon5WindPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon2LancePower(m); }break;
			case Deca.ID:
				if (i == DEFEND) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon4BowPower(m); } break;
			case Donu.ID:
				if (i == BUFF) { w = new Weapon4BowPower(m); }
				if (i == ATTACK) { w = new Weapon8DarkPower(m); } break;

			// Standard Act 4
			case SpireSpear.ID:
				if (i == ATTACK_DEBUFF) { w = new Weapon6FirePower(m); }
				if (i == BUFF) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK) { w = new Weapon2LancePower(m); } break;
			case SpireShield.ID:
				if (i == ATTACK_DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == DEFEND) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon3AxePower(m); } break;
			case CorruptHeart.ID:
				if (i == STRONG_DEBUFF) { w = new Weapon0NeutralPower(m); }
				if (i == BUFF) { w = new Weapon0NeutralPower(m); }
				if (i == ATTACK) { w = new StrengthPower(m, 999); } break;


			// Downfall Events and Boss Minions
			case "downfall:Augmenter":
				if (i == ATTACK_BUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon8DarkPower(m); } break;
			case "downfall:FaceTrader":
				if (i == ATTACK_DEBUFF) { w = new Weapon0NeutralPower(m); } // Three possible attacks on turn 1. :(
				if (i == DEFEND_BUFF) { w = new StrengthPower(m, 999); } break; // Requires a patch.
			case "downfall:LadyInBlue":
				if (i == ATTACK_BUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon8DarkPower(m); }
				if (i == BUFF) { w = new Weapon7ThunderPower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon5WindPower(m); } break;
			case "downfall:GrowingTotem": w = new Weapon1SwordPower(m); break;
			case "downfall:ForgetfulTotem": w = new Weapon2LancePower(m); break;
			case "downfall:ChangingTotem": w = new Weapon3AxePower(m); break;
			case "downfall:FleeingMerchant":
				if (i == DEFEND) { w = new Weapon4BowPower(m); }
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == BUFF) { w = new Weapon8DarkPower(m); }
				if (i == ESCAPE) { w = new Weapon0NeutralPower(m); } break;
			case "downfall:MushroomRed": w = new Weapon2LancePower(m); break;
			case "downfall:MushroomWhite": w = new Weapon5WindPower(m); break;
			case "downfall:MushroomPurple": w = new Weapon8DarkPower(m); break;
			case "downfall:Fortification": w = new Weapon0NeutralPower(m); break;
			case "downfall:FuzzyLouseTangerine":
				if (i == SLEEP) { w = new Weapon0NeutralPower(m); }
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == BUFF) { w = new Weapon7ThunderPower(m); } break;

			// Downfall Act 4
			case "downfall:GauntletIronclad":
				if (i == ATTACK) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon3AxePower(m); }
				if (i == DEFEND) { w = new Weapon6FirePower(m); }
				if (i == BUFF) { w = new Weapon6FirePower(m); } break;
			case "downfall:GauntletSilent":
				if (i == ATTACK) { w = new Weapon5WindPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon5WindPower(m); }
				if (i == DEFEND_DEBUFF) { w = new Weapon2LancePower(m); }
				if (i == DEFEND) { w = new Weapon8DarkPower(m); }
				if (i == BUFF) { w = new Weapon8DarkPower(m); } break;
			case "downfall:GauntletDefect":
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon7ThunderPower(m); }
				if (i == DEFEND) { w = new Weapon4BowPower(m); }
				if (i == BUFF) { w = new Weapon3AxePower(m); } break;
			case "downfall:GauntletWatcher":
				if (i == ATTACK) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon2LancePower(m); }
				if (i == DEFEND) { w = new Weapon7ThunderPower(m); }
				if (i == BUFF) { w = new Weapon6FirePower(m); } break;
			case "downfall:GauntletHermit":
				if (i == ATTACK) { w = new Weapon4BowPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon4BowPower(m); }
				if (i == DEFEND) { w = new Weapon8DarkPower(m); }
				if (i == BUFF) { w = new Weapon5WindPower(m); } break;
			case "downfall:NeowBoss": w = new StrengthPower(m, 999); break;
			case "downfall:NeowBossFinal": case "downfall:Neow4Life30Heal":
				if (i == ATTACK) { w = new Weapon0NeutralPower(m); } // Neow's attacks are Neutral, unlike the Heart.
				if (i == STRONG_DEBUFF) { w = new Weapon8DarkPower(m); }
				if (i == BUFF) { w = new Weapon4BowPower(m); } break;

			// Downfall Bosses (Note: intention cannot be read from cards on turn start)
			case "downfall:Ironclad":
				switch (AbstractDungeon.actNum) {
					case 1: w = new Weapon1SwordPower(m); break;
					case 2: w = new Weapon3AxePower(m); break;
					case 3: w = new Weapon6FirePower(m); break;
				} break;
			case "downfall:Silent": case "downfall:MirrorImageSilent":
				switch (AbstractDungeon.actNum) {
					case 1: w = new Weapon5WindPower(m); break;
					case 2: if (GameActionManager.turn == 1) { w = new Weapon8DarkPower(m); }
							else { w = new StrengthPower(m, 999); } break;
					case 3: w = new Weapon2LancePower(m); break;
				} break;
			case "downfall:Defect":
				switch (AbstractDungeon.actNum) {
					case 1: w = new Weapon7ThunderPower(m); break;
					case 2: w = new Weapon4BowPower(m); break;
					case 3: w = new Weapon3AxePower(m); break;
				} break;
			case "downfall:Watcher":
				switch (AbstractDungeon.actNum) {
					case 1: w = new Weapon2LancePower(m); break;
					case 2: w = new Weapon6FirePower(m); break;
					case 3: w = new Weapon1SwordPower(m); break;
				} break;
			case "downfall:Hermit":
				switch (AbstractDungeon.actNum) {
					case 1: w = new Weapon4BowPower(m); break;
					case 2: w = new Weapon5WindPower(m); break;
					case 3: w = new Weapon8DarkPower(m); break;
				} break;
			case "downfall:CharBossMerchant": w = new Weapon7ThunderPower(m); break;


			// Spire Biomes
			case "anniv6:Eruptor": w = new Weapon5WindPower(m); break;
			case "anniv6:HeatBlister":
				if (i == BUFF) { w = new Weapon5WindPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == ATTACK) { w = new Weapon6FirePower(m); } break;
			case "anniv6:SunStoneShard":
				if (i == BUFF) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK) { w = new Weapon6FirePower(m); }
				if (i == DEFEND_BUFF) { w = new Weapon2LancePower(m); } break;
			case "anniv6:UnstableSunstone":
				if (i == BUFF) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); } break;
			case "anniv6:Cole":
				if (i == DEFEND) { w = new Weapon7ThunderPower(m); }
				if (i == DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == UNKNOWN) { w = new Weapon8DarkPower(m); } break;
			case "anniv6:Spruce":
				if (i == ATTACK) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon2LancePower(m); }
				if (i == UNKNOWN) { w = new Weapon8DarkPower(m); } break;
			case "anniv6:Hypothema":
				if (i == BUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); }
				if (i == UNKNOWN) { w = new Weapon8DarkPower(m); } break;
			case "anniv6:Steward":
				if (i == DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == ATTACK_BUFF) { w = new Weapon3AxePower(m); }
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); } break;
			case "anniv6:BanditLieutenant":
				if (i == ATTACK_DEBUFF) { w = new Weapon3AxePower(m); }
				if (i == DEBUFF) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); } break;
			case "anniv6:ThiefKing":
				if (i == DEBUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == DEFEND_BUFF) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK) { w = new Weapon2LancePower(m); } break;
			case "anniv6:Junkbot": w = new Weapon7ThunderPower(m); break;
			case "anniv6:Peddler":
				if (i == UNKNOWN) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon7ThunderPower(m); }
				if (i == BUFF) { w = new Weapon2LancePower(m); }
				if (i == ATTACK) { w = new Weapon5WindPower(m); }
				if (i == ESCAPE) { w = new StrengthPower(m, 999); } break;

			case "anniv6:ArmoredGremlin": w = new Weapon2LancePower(m); break;
			case "anniv6:GremlinBarbarian": w = new Weapon8DarkPower(m); break;
			case "anniv6:GremlinAssassin": w = new Weapon5WindPower(m); break;
			case "anniv6:GremlinCook":
				if (i == BUFF) { w = new Weapon6FirePower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon3AxePower(m); } break;
			case "anniv6:GremlinDog":
				if (i == ATTACK_DEBUFF) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon5WindPower(m); } break;
			case "anniv6:GremlinElder":
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon1SwordPower(m); }
				if (i == UNKNOWN) { w = new StrengthPower(m, 999); }
				if (i == STRONG_DEBUFF) { w = new Weapon8DarkPower(m); } break;
			case "anniv6:ChubbyGremlin": w = new StrengthPower(m, 999); break; // I haven't found this enemy yet.
			case "anniv6:GremlinHealer": w = new StrengthPower(m, 999); break; // I haven't found this enemy yet.
			case "anniv6:GremlinRockTosser": w = new StrengthPower(m, 999); break; // I haven't found this enemy yet.
			case "anniv6:GremlinJerk": w = new Weapon3AxePower(m); break;
			case "anniv6:GremlinNib": w = new Weapon3AxePower(m); break;
			case "anniv6:GremlinRiderRed": case "anniv6:GremlinRiderGreen": case "anniv6:GremlinRiderBlue":
				if (i == ATTACK) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon5WindPower(m); }
				if (i == ATTACK_BUFF) { w = new Weapon5WindPower(m); } break;
			case "anniv6:GremlinCannon":
				if (i == UNKNOWN) { w = new Weapon6FirePower(m); }
				if (i == DEBUFF) { w = new Weapon2LancePower(m); }
				if (i == ATTACK) { w = new Weapon4BowPower(m); } break;
			case "anniv6:GremlinArchmage":
				if (i == DEFEND) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK) { w = new Weapon6FirePower(m); }
				if (i == UNKNOWN) { w = new Weapon4BowPower(m); } break;

			case "anniv6:GraftedWorm": w = new Weapon1SwordPower(m); break;
			case "anniv6:DreadMoth":
				if (i == DEFEND) { w = new Weapon2LancePower(m); }
				if (i == ATTACK) { w = new Weapon4BowPower(m); } break;
			case "anniv6:WhisperingWraith":
				if (i == ATTACK_DEFEND) { w = new Weapon5WindPower(m); }
				if (i == ATTACK) { w = new Weapon8DarkPower(m); }
				if (i == BUFF) { w = new Weapon7ThunderPower(m); } break;
			case "anniv6:StygianBoar":
				if (i == ATTACK_BUFF) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK) {
					if (GameActionManager.turn == 1) { w = new Weapon2LancePower(m); }
					else { w = new StrengthPower(m, 999); }} break;
			case "anniv6:CroakingBrute": { w = new Weapon4BowPower(m); } break;
			case "anniv6:CroakingPelter":
				if (i == ATTACK_DEFEND) { w = new Weapon2LancePower(m); }
				if (i == ATTACK) { w = new Weapon8DarkPower(m); } break;
			case "anniv6:CroakingSeer":
				if (i == DEFEND) { w = new Weapon3AxePower(m); }
				if (i == ATTACK) { w = new Weapon8DarkPower(m); } break;
			case "anniv6:VoidReaper":
				if (i == ATTACK_DEFEND) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK) { w = new Weapon3AxePower(m); } break;
			case "anniv6:UnboundAbyssal":
				if (i == ATTACK_DEBUFF) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK) { w = new Weapon1SwordPower(m); }
				if (i == BUFF) { w = new Weapon2LancePower(m); } break;
			case "anniv6:VoidCorruption":
				if (i == DEBUFF) { w = new Weapon1SwordPower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK) { w = new Weapon8DarkPower(m); } break;
			case "anniv6:VoidBeast":
				if (i == ATTACK) { w = new Weapon8DarkPower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon2LancePower(m); } break;
			case "anniv6:Hydra":
				if (i == ATTACK_DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == ATTACK) { w = new StrengthPower(m, 999); } break; // TODO: Patch.
			case "anniv6:Behemoth":
				if (i == UNKNOWN) { w = new Weapon6FirePower(m); }
				if (i == MAGIC) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK) { w = new Weapon5WindPower(m); } break;
			case "anniv6:Hexasnake":
				if (i == STRONG_DEBUFF) { w = new Weapon2LancePower(m); }
				if (i == ATTACK_DEBUFF) { w = new StrengthPower(m, 999); } break; // TODO: Patch.
			case "anniv6:PrimevalQueen":
				if (i == ATTACK) { w = new Weapon4BowPower(m); }
				if (i == UNKNOWN) { w = new Weapon7ThunderPower(m); }
				if (i == DEFEND_BUFF) { w = new Weapon3AxePower(m); } break;
			case "anniv6:RoyalProtector":
				if (i == DEFEND) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK) { w = new Weapon8DarkPower(m); }
				if (i == ATTACK_DEBUFF) { w = new Weapon6FirePower(m); } break;
			case "anniv6:Hatchling":
				if (i == ATTACK_DEFEND) { w = new Weapon5WindPower(m); }
				if (i == ATTACK) { w = new Weapon1SwordPower(m); }
				if (i == DEBUFF) { w = new Weapon6FirePower(m); }
				if (i == UNKNOWN) { w = new Weapon2LancePower(m); } break;
			case "anniv6:WarGolem": {
				if (GameActionManager.turn == 1) { w = new Weapon7ThunderPower(m); }
				else { w = new StrengthPower(m, 999); }} break;
			case "anniv6:ElementalPortal":
				if (i == MAGIC) { w = new Weapon8DarkPower(m); }
				if (i == BUFF) { w = new Weapon3AxePower(m); }
				if (i == STRONG_DEBUFF) { w = new Weapon4BowPower(m); }
				if (i == ATTACK) { w = new Weapon8DarkPower(m); } break;
			case "anniv6:OrbOfFire":
				if (i == ATTACK_DEBUFF) { w = new Weapon6FirePower(m); }
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == DEBUFF) { w = new Weapon5WindPower(m); } break;
			case "anniv6:LivingStormcloud":
				if (i == ATTACK) { w = new Weapon7ThunderPower(m); }
				if (i == ATTACK_BUFF) { w = new Weapon5WindPower(m); }
				if (i == BUFF) { w = new Weapon2LancePower(m); } break;
			case "anniv6:OpulentOffering":
				if (i == ATTACK_BUFF) { w = new Weapon1SwordPower(m); }
				if (i == DEFEND) { w = new Weapon4BowPower(m); }
				if (i == ATTACK_DEFEND) { w = new Weapon3AxePower(m); } break;
			case "anniv6:ShimmeringMirage":
				if (i == ATTACK_DEBUFF) { w = new Weapon2LancePower(m); }
				if (i == BUFF) { w = new Weapon1SwordPower(m); }
				if (i == ATTACK_BUFF) { w = new Weapon6FirePower(m); } break;
		}
		return w;
	}
}