package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.stance.DivinityStanceChangeParticle;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.powers.LoseFocusPower;
import tactician.util.CardStats;

import java.util.ArrayList;

public class RallySpectrum extends TacticianCard {
    public static final String ID = makeID(RallySpectrum.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public RallySpectrum() {
        super(ID, info);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new RallyStrength(() ->  {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
        }));
        easyCardList.add(new RallyMagic(() ->  {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        }));
        easyCardList.add(new RallyDefense(() ->  {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(this.magicNumber), this.magicNumber));
        }));
        addToBot(new EasyModalChoiceAction(easyCardList));
        addToBot(new PlaySoundAction("tactician:RallySpectrum", 1.00f));
        for (int i = 0; i < 6; i++) {
            AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.RED, p.hb.cX, p.hb.cY));
            AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.BLUE, p.hb.cX, p.hb.cY));
            AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.GREEN, p.hb.cX, p.hb.cY));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new RallySpectrum(); }
}