package tactician.cards.additional;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.effects.cards.LunaEffect;
import tactician.effects.cards.SolEffect;
import tactician.powers.DeflectPower;
import tactician.powers.SolPower;
import tactician.util.CardStats;

public class Sol extends TacticianCard {
	public static final String ID = makeID(Sol.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.UNCOMMON,
			CardTarget.SELF,
			1
	);

	public Sol() {
		super(ID, info);
		setBlock(0, 3);
		setMagic(4, 0);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.upgraded) { addToBot(new GainBlockAction(p, this.block)); }
		addToBot(new VFXAction(new SolEffect()));
		addToBot(new PlaySoundAction("tactician:Sol", 1.67F));
		addToBot(new ApplyPowerAction(p, p, new SolPower(1), 1));
		addToBot(new WaitAction(0.33F));
		addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() { return new Sol(); }
}