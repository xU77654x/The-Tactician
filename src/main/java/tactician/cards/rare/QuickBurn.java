package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
			addToBot(new ApplyPowerAction(p, p, new QuickBurnPower(p)));
			AbstractDungeon.effectList.add(new PlayVoiceEffect("QuickBurn"));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new QuickBurn(); }


}