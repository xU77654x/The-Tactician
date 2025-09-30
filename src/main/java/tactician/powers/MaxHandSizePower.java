package tactician.powers;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.powerPath;

public class MaxHandSizePower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("MaxHandSizePower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/MaxHandSizeUp_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("MaxHandSizeUp.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public MaxHandSizePower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 85, 85);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		updateDescription();
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void playApplyPowerSfx() {}

		@Override
	public void onInitialApplication() {
		super.onInitialApplication();
		BaseMod.MAX_HAND_SIZE += this.amount;
	}

	@Override
	public void onRemove() {
		super.onRemove();
		BaseMod.MAX_HAND_SIZE -= this.amount;
	}

	@Override
	public void stackPower(int stackAmount) {
		super.stackPower(stackAmount);
		BaseMod.MAX_HAND_SIZE += stackAmount;
	}

	@Override
	public void reducePower(int reduceAmount) {
		super.reducePower(reduceAmount);
		BaseMod.MAX_HAND_SIZE -= reduceAmount;
	}

	@Override
	public void onVictory() {
		super.onVictory();
		BaseMod.MAX_HAND_SIZE -= this.amount;
	}
}