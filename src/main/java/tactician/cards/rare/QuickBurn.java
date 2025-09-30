package tactician.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.powers.QuickBurnPower;
import tactician.util.CardStats;

public class QuickBurn extends TacticianCard {
	public static final String ID = makeID(QuickBurn.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.POWER,
			CardRarity.RARE,
			CardTarget.SELF,
			3
	);

	public QuickBurn() {
		super(ID, info);
		setCostUpgrade(2);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!p.hasPower(QuickBurnPower.POWER_ID)) {
			addToBot(new PlaySoundAction("tactician:QuickBurn", 1.00f));
			addToBot(new ApplyPowerAction(p, p, new QuickBurnPower(p)));
			addToBot(new VFXAction(p, new VerticalAuraEffect(Color.FIREBRICK, p.hb.cX, p.hb.cY), 0.33F));
			addToBot(new VFXAction(p, new VerticalAuraEffect(Color.YELLOW, p.hb.cX, p.hb.cY), 0.33F));
			addToBot(new VFXAction(p, new VerticalAuraEffect(Color.CORAL, p.hb.cX, p.hb.cY), 0.0F));
			addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.RED), 0.0F, true));

			AbstractDungeon.effectList.add(new PlayVoiceEffect("QuickBurn"));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new QuickBurn(); }


}