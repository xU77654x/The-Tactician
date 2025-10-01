package tactician.cards.basic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.actions.HandSelectAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class Veteran extends TacticianCard {
    public static final String ID = makeID(Veteran.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );

    public Veteran() {
        super(ID, info);
        setMagic(2, 1);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new ExhaustAction(1, false));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        }
        else {
            addToBot(new HandSelectAction(1, (c) -> true, list -> {}, list -> {
                int statGain = this.magicNumber;
                for (AbstractCard c : list) {
                    AbstractDungeon.player.hand.moveToExhaustPile(c);
                    if (c.type == AbstractCard.CardType.CURSE) { statGain = 0; }
                }
                if (statGain > 0) {
                    addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, statGain), statGain));
                    addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, statGain), statGain));
                }
            }, cardStrings.EXTENDED_DESCRIPTION[0],false,false,false));
        }
        addToTop(new PlaySoundAction("tactician:Veteran", 1.10f));
    }

    @Override
    public AbstractCard makeCopy() { return new Veteran(); }
}