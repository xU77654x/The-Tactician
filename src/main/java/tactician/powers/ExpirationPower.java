package tactician.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tactician.TacticianMod;
import tactician.actions.PlaySoundAction;
import tactician.util.TextureLoader;
import static tactician.TacticianMod.powerPath;

public class ExpirationPower extends AbstractPower {
	public static final String POWER_ID = TacticianMod.makeID("ExpirationPower");
	private static final Texture tex84 = TextureLoader.getTexture(powerPath("large/Expiration_Large.png"));
	private static final Texture tex32 = TextureLoader.getTexture(powerPath("Expiration.png"));
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public ExpirationPower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 64, 64);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
		this.updateDescription();
	}

	@Override
	public void updateDescription() { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }

	@Override
	public void playApplyPowerSfx() { addToTop(new PlaySoundAction("tactician:Expiration", 1.25f)); }

	@Override
	public void onExhaust(AbstractCard card) {
		addToTop(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE));
	}
}