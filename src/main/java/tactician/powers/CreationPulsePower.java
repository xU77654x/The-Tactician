package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import tactician.TacticianMod;
import tactician.actions.PlaySoundAction;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.powerPath;

public class CreationPulsePower extends AbstractPower implements OnReceivePowerPower {
	public static final String POWER_ID = TacticianMod.makeID("CreationPulsePower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/CreationPulse_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("CreationPulse.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public CreationPulsePower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 75, 75);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		this.updateDescription();
	}

	@Override
	public void updateDescription() { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3]; }

	@Override
	public void playApplyPowerSfx() { addToBot(new PlaySoundAction("tactician:CreationPulse", 1.00f)); }

	@Override
	public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) { return true; }

	@Override
	public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
		if (power.ID.equals(LoseStrengthPower.POWER_ID) && stackAmount > 0) { for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
			addToBot(new ApplyPowerAction(mo, this.owner, new VulnerablePower(mo, this.amount, false), this.amount, true, AbstractGameAction.AttackEffect.NONE)); }
		}
		if (power.ID.equals(LoseDexterityPower.POWER_ID) && stackAmount > 0) { for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
			addToBot(new ApplyPowerAction(mo, this.owner, new WeakPower(mo, this.amount, false), this.amount, true, AbstractGameAction.AttackEffect.NONE)); }
		}
		if (power.ID.equals(LoseFocusPower.POWER_ID) && stackAmount > 0) { for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
			addToBot(new ApplyPowerAction(mo, this.owner, new LockOnPower(mo, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE)); }
		}
		return OnReceivePowerPower.super.onReceivePowerStacks(power, target, source, stackAmount);
	}
}