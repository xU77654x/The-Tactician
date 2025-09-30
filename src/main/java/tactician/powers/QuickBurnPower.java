package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.actions.PlaySoundAction;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.powerPath;

public class QuickBurnPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("QuickBurnPower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/QuickBurn_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("QuickBurn.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public QuickBurnPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 109, 90);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		this.updateDescription();
	}

	@Override
	public void updateDescription() { this.description = DESCRIPTIONS[0]; }

	@Override
	public void playApplyPowerSfx() { addToTop(new PlaySoundAction("tactician:QuickBurn", 1.00f)); }

	@Override
	public void onCardDraw(AbstractCard card) { if (card.type == AbstractCard.CardType.ATTACK) { card.setCostForTurn(-9); }}

	@Override
	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (card.type == AbstractCard.CardType.ATTACK) {
			flash();
			action.exhaustCard = true;
		}
	}
}