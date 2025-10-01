package tactician.cards.common;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import tactician.actions.ArmsthriftAction;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;
import java.util.ArrayList;

public class Armsthrift extends TacticianCard {
    public static final String ID = makeID(Armsthrift.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Armsthrift() {
        super(ID, info);
        setMagic(4, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));

        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new UpgradeAttacks(() -> addToBot(new ArmsthriftAction(0))));
        easyCardList.add(new UpgradeSkills(() -> addToBot(new ArmsthriftAction(1))));
        easyCardList.add(new UpgradePowers(() -> addToBot(new ArmsthriftAction(2))));
        addToBot(new EasyModalChoiceAction(easyCardList));
        addToTop(new PlaySoundAction("tactician:Armsthrift", 1.25f));
        addToBot(new VFXAction(new UpgradeShineEffect(p.hb.cX, p.hb.cY)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Armsthrift();
    }
}