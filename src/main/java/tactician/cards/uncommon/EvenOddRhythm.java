package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.other.Anathema;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class EvenOddRhythm extends TacticianCard {
    public static final String ID = makeID(EvenOddRhythm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public EvenOddRhythm() {
        super(ID, info);
        setBlock(12, 4);
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (GameActionManager.turn % 2 == 0) { addToBot(new PlaySoundAction("tactician:EvenRhythm", 1.00f)); }
        else { addToBot(new PlaySoundAction("tactician:OddRhythm", 1.00f)); }
        addToBot(new GainBlockAction(p, p, this.block));
        if (GameActionManager.turn % 2 == 0) { addToBot(new MakeTempCardInHandAction(new Hex(), 1)); }
        else { addToBot(new MakeTempCardInHandAction(new Anathema(), 1)); }
    }

    public void onMoveToDiscard() {
        if (upgraded) { this.name = cardStrings.NAME + "+"; }
        else { this.name = cardStrings.NAME; }
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeTitle();
        initializeDescription();
        loadCardImage("tactician/images/cards/skill/EvenOddRhythm.png");
    }

    /*
    protected Texture getPortraitImage() {
        if (GameActionManager.turn % 2 == 0) { return ImageMaster.loadImage("tactician/images/cards/skill/EvenOddRhythm_Panne_p.png"); }
        else if (GameActionManager.turn % 2 == 1) { return ImageMaster.loadImage("tactician/images/cards/skill/EvenOddRhythm_Nowi_p.png"); }
        return super.getPortraitImage();
    } // Change the portrait image based on the turn order. Not required here due to the card preview always in the deck, shop, or Compendium--where it is always turn-neutral. */

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (GameActionManager.turn % 2 == 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            if (upgraded) { this.name = cardStrings.EXTENDED_DESCRIPTION[0] + "+"; }
            else { this.name = cardStrings.EXTENDED_DESCRIPTION[0]; }
            loadCardImage("tactician/images/cards/skill/EvenOddRhythm_Panne.png");
        }
        else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            if (upgraded) { this.name = cardStrings.EXTENDED_DESCRIPTION[2] + "+"; }
            else { this.name = cardStrings.EXTENDED_DESCRIPTION[2]; }
            loadCardImage("tactician/images/cards/skill/EvenOddRhythm_Nowi.png");
        }
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (GameActionManager.turn % 2 == 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            if (upgraded) { this.name = cardStrings.EXTENDED_DESCRIPTION[0] + "+"; }
            else { this.name = cardStrings.EXTENDED_DESCRIPTION[0]; }
		}
        else {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            if (upgraded) { this.name = cardStrings.EXTENDED_DESCRIPTION[2] + "+"; }
            else { this.name = cardStrings.EXTENDED_DESCRIPTION[2]; }
		}
	}

    @Override
    public AbstractCard makeCopy() { return new EvenOddRhythm(); }
}