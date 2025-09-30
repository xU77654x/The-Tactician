package tactician.powers.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.powers.ChaosStylePower;
import tactician.powers.ZealPower;
import tactician.util.TextureLoader;
import tactician.util.Wiz;
import static tactician.TacticianMod.powerPath;

public class Weapon4BowPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("Weapon4BowPower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/Weapon4Bow_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("Weapon4Bow.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public AbstractPlayer p;

	public Weapon4BowPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 60, 60);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 40, 40);
		this.updateDescription();
		this.p = AbstractDungeon.player;
		priority = -1000;
	}

	@Override
	public void updateDescription() { this.description = DESCRIPTIONS[0];}

	@Override
	public void playApplyPowerSfx() { }

	@Override
	public void onInitialApplication() {
		super.onInitialApplication();
		if (owner.hasPower(Weapon0NeutralPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon0NeutralPower.POWER_ID)); }
		if (owner.hasPower(Weapon1SwordPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon1SwordPower.POWER_ID)); }
		if (owner.hasPower(Weapon2LancePower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon2LancePower.POWER_ID)); }
		if (owner.hasPower(Weapon3AxePower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon3AxePower.POWER_ID)); }
		if (owner.hasPower(Weapon5WindPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon5WindPower.POWER_ID)); }
		if (owner.hasPower(Weapon6FirePower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon6FirePower.POWER_ID)); }
		if (owner.hasPower(Weapon7ThunderPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon7ThunderPower.POWER_ID)); }
		if (owner.hasPower(Weapon8DarkPower.POWER_ID)) { addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Weapon8DarkPower.POWER_ID)); }
		if (owner.hasPower(ChaosStylePower.POWER_ID)) { addToTop(new GainBlockAction(this.owner, this.owner.getPower(ChaosStylePower.POWER_ID).amount)); }
	}

	@Override
	public float atDamageGive(float damage, DamageInfo.DamageType type) {
		if (type == DamageInfo.DamageType.NORMAL) {
			if (owner != this.p) {
				if (this.p.hasPower(ZealPower.POWER_ID)) { return damage -3; }
				else if (this.p.hasPower(Weapon5WindPower.POWER_ID) || this.p.hasPower(Weapon6FirePower.POWER_ID) || this.p.hasPower(Weapon7ThunderPower.POWER_ID)) { return damage -3; }
				else if (this.p.hasPower(Weapon1SwordPower.POWER_ID) || this.p.hasPower(Weapon2LancePower.POWER_ID) || this.p.hasPower(Weapon3AxePower.POWER_ID)) { return damage +3; }
			}
		}
		return damage;
	}

	@Override
	public void onVictory() { Wiz.setNextCombatWeapon(4); }
}