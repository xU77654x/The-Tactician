package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.powerPath;

public class TempOrbSlotPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("TempOrbSlotPower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/TempOrbSlot_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("TempOrbSlot.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public TempOrbSlotPower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = true;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 96, 96);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		this.updateDescription();
	}

	@Override
	public void updateDescription() { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }

	@Override
	public void onInitialApplication() { addToBot(new IncreaseMaxOrbAction(1)); }

	@Override
	public void atStartOfTurn() {
		this.amount--;
		if (this.amount == 0) {
			addToBot(new DecreaseMaxOrbAction(1));
			addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
		}
	}
}