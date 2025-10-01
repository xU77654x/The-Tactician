package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.stance.DivinityStanceChangeParticle;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.FiendishBlowPower;
import tactician.powers.LoseFocusPower;
import tactician.util.CardStats;

public class FiendishBlow extends TacticianCard {
    public static final String ID = makeID(FiendishBlow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public FiendishBlow() {
        super(ID, info);
        setMagic(3, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new PlaySoundAction("tactician:StatIncreaseFE", 1.00f));
        addToBot(new ApplyPowerAction(p, p, new FiendishBlowPower(this.magicNumber), this.magicNumber));
        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(this.magicNumber), this.magicNumber));
        }
        for (int i = 0; i < 15; i++) { AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.BLUE, p.hb.cX, p.hb.cY)); }
    }

    @Override
    public AbstractCard makeCopy() { return new FiendishBlow(); }
}