package tactician.cards.basic;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightBulbEffect;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

public class Solidarity extends TacticianCard {
    public static final String ID = makeID(Solidarity.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            2
    );

    public Solidarity() {
        super(ID, info);
        setMagic(8, 4);
        this.cardsToPreview = new Hex();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:Solidarity", 0.75f));
        addToBot(new VFXAction(new LightBulbEffect(p.hb), 0.10F));
        addToBot(new DrawCardAction(2));
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
        addToBot(new MakeTempCardInHandAction(new Hex(), 1));
    }

    @Override
    public AbstractCard makeCopy() { return new Solidarity(); }
}