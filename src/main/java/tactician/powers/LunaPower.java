package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.powerPath;

public class LunaPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("LunaPower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/Luna_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("Luna.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public LunaPower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = true;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 96, 96);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		updateDescription();
	}

	@Override
	public void updateDescription() {
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3] + DESCRIPTIONS[1]; }
	}

	@Override
	public void playApplyPowerSfx() { } // This is present in the Luna card due to the timing of the effects.

	@Override
	public void onUseCard(AbstractCard c, UseCardAction action) {
		if (c.type == AbstractCard.CardType.ATTACK) {
			(AbstractDungeon.player.orbs.get(0)).onStartOfTurn();
			(AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
		}
	}

	@Override
	public void atEndOfRound() {
		if (this.amount == 0) { addToBot((new RemoveSpecificPowerAction(this.owner, this.owner, LunaPower.POWER_ID))); }
		else { addToBot(new ReducePowerAction(this.owner, this.owner, LunaPower.POWER_ID, 1)); }
	}
}