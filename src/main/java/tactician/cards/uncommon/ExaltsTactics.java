package tactician.cards.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.MaxHandSizePower;
import tactician.util.CardStats;
import java.util.ArrayList;
import java.util.List;

public class ExaltsTactics extends TacticianCard {
    public static final String ID = makeID(ExaltsTactics.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public ExaltsTactics() {
        super(ID, info);
        setMagic(2, 1);

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MaxHandSizePower(this.magicNumber), this.magicNumber));
        addToBot(new PlaySoundAction("tactician:ExaltsTactics", 1.00f));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> toolTipList = new ArrayList<>();
        toolTipList.add(new TooltipInfo(cardStrings.EXTENDED_DESCRIPTION[0], cardStrings.EXTENDED_DESCRIPTION[1]));
        return toolTipList;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExaltsTactics();
    }
}