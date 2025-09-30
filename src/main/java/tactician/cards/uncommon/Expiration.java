package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.MedicalKit;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.TempBlueCandle;
import tactician.cards.cardchoice.TempMedicalKit;
import tactician.cards.cardchoice.TempCirclet;
import tactician.character.TacticianRobin;
import tactician.powers.ExpirationPower;
import tactician.util.CardStats;
import tactician.util.FragileRelics;
import java.util.ArrayList;

public class Expiration extends TacticianCard {
    public static final String ID = makeID(Expiration.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Expiration() {
        super(ID, info);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Boolean.FALSE.equals(p.hasRelic(BlueCandle.ID)) || Boolean.FALSE.equals(p.hasRelic(MedicalKit.ID))) {
            ArrayList<AbstractCard> easyCardList = new ArrayList<>();
            if (Boolean.FALSE.equals(p.hasRelic(BlueCandle.ID))) { easyCardList.add(new TempBlueCandle(() -> FragileRelics.obtainFragileRelic(new BlueCandle()))); }
            if (Boolean.FALSE.equals(p.hasRelic(MedicalKit.ID))) { easyCardList.add(new TempMedicalKit(() -> FragileRelics.obtainFragileRelic(new MedicalKit()))); }
            easyCardList.add(new TempCirclet(() -> FragileRelics.obtainFragileRelic(new Circlet())));
            addToTop(new EasyModalChoiceAction(easyCardList));
        }
        else { addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 1.0F, 2.0F)); }
        addToBot(new PlaySoundAction("tactician:Expiration", 1.25f));
        addToBot(new ApplyPowerAction(p, p, new ExpirationPower(this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new Expiration(); }
}