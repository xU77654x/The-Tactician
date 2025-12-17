package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.TacticianMod;
import tactician.actions.PlaySoundAction;
import tactician.util.TextureLoader;
import java.util.Objects;
import static tactician.TacticianMod.powerPath;

public class DelivererPower extends TwoAmountPower implements OnReceivePowerPower {
	public static final String POWER_ID = TacticianMod.makeID("DelivererPower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/Deliverer_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("Deliverer.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private int secondGain;

	public DelivererPower(int block, int stat) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = block;
		this.amount2 = stat;
		this.secondGain = stat;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 96, 96);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		this.updateDescription();
	}

	@Override
	public void updateDescription() { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2]; }

	@Override
	public void playApplyPowerSfx() { }

	@Override
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		this.amount2 += secondGain;
	}

	@Override
	public boolean onReceivePower(AbstractPower paramAbstractPower, AbstractCreature paramAbstractCreature1, AbstractCreature paramAbstractCreature2) { return true; }

	@Override
	public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
		if (Objects.equals(power.ID, StrengthPower.POWER_ID) && stackAmount < 0) {
			addToBot(new GainBlockAction(AbstractDungeon.player, this.amount));
			addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.amount2), this.amount2));
		}
		else if (Objects.equals(power.ID, DexterityPower.POWER_ID) && stackAmount < 0) {
			addToBot(new GainBlockAction(AbstractDungeon.player, this.amount));
			addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.amount2), this.amount2));
		}
		return stackAmount;
	}
}