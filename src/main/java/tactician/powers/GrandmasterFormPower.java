package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import tactician.TacticianMod;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.powerPath;

public class GrandmasterFormPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("GrandmasterFormPower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/GrandmasterForm_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("GrandmasterForm.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private int counter;

	public GrandmasterFormPower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.counter = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 96, 96);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		updateDescription();
	}

	@Override
	public void updateDescription() { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }

	@Override
	public void playApplyPowerSfx() { }

	@Override
	public void stackPower(int stackAmount) {
		super.stackPower(stackAmount);
		this.counter += stackAmount;
	}

	@SuppressWarnings("UnnecessaryReturnStatement")
	@Override
	public void onExhaust(AbstractCard card) {
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
			if (this.counter <= 0) { return; }
			else {
				flash();
				addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, this.amount), this.amount));
				this.counter--;
			}
		}
	}

	@Override
	public void atStartOfTurn() { this.counter = this.amount; }



}