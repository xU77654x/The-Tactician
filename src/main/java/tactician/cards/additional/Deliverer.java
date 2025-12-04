package tactician.cards.additional;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.DelivererPower;
import tactician.util.CardStats;

public class Deliverer extends TacticianCard {
	public static final String ID = makeID(Deliverer.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.POWER,
			CardRarity.RARE,
			CardTarget.SELF,
			2
	);

	public Deliverer() {
		super(ID, info);
		setMagic(3, 0);
		setCustomVar("magicStat", 1, 0);
		setInnate(false, true);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		addToBot(new PlaySoundAction("tactician:Deliverer", 1.00F));
		// TODO: Deliverer VFX
		addToBot(new ApplyPowerAction(p, p, new DelivererPower(this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() { return new Deliverer(); }
}