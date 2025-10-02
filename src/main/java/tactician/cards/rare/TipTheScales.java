package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.other.Anathema;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.util.CardStats;

public class TipTheScales extends TacticianCard {
    public static final String ID = makeID(TipTheScales.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            -2
    );

    public TipTheScales() {
        super(ID, info);
        setMagic(2, 1);
        setSelfRetain(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        addToTop(new PlaySoundAction("tactician:TipTheScales", 1.33f));
        addToBot(new VFXAction(new PlayVoiceEffect("TipTheScales")));
        addToBot(new MakeTempCardInHandAction(new Anathema(), 1));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TipTheScales();
    }
}