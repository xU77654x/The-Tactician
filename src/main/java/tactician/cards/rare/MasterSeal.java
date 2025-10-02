package tactician.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.other.Anathema;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class MasterSeal extends TacticianCard {
    public static final String ID = makeID(MasterSeal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public MasterSeal() {
        super(ID, info);
        setMagic(1, 0);
        setCostUpgrade(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:MasterSeal", 0.75f));
        addToBot(new VFXAction(p, new VerticalAuraEffect(Color.GOLD.cpy(), p.hb.cX, p.hb.cY), 0.50F));
        addToBot(new ApplyPowerAction(p, p, new RitualPower(p, 1, true), 1));
        addToBot(new IncreaseMaxOrbAction(this.magicNumber));
        addToBot(new MakeTempCardInHandAction(new Anathema(), 1));
        addToBot(new MakeTempCardInHandAction(new Hex(), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MasterSeal();
    }
}