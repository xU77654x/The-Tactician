package tactician.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.effects.cards.fire.IgnisEffect;
import tactician.util.CardStats;

public class Ignis extends TacticianCard {
    public static final String ID = makeID(Ignis.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    public AbstractPlayer p;

    public Ignis() {
        super(ID, info);
        setExhaust(true, false);
        setMagic(0, 5);
        this.p = AbstractDungeon.player;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int oldStrength = 0;
        int oldFocus = 0;
        addToBot(new VFXAction(new IgnisEffect(p, "tactician:Ignis", 1.50f)));

        if (this.p.hasPower(StrengthPower.POWER_ID)) { oldStrength = this.p.getPower(StrengthPower.POWER_ID).amount; }
        if (this.p.hasPower(FocusPower.POWER_ID)) { oldFocus = this.p.getPower(FocusPower.POWER_ID).amount; }
        
        if (this.p.hasPower(StrengthPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new FocusPower(p, oldStrength), oldStrength)); }
        if (this.p.hasPower(FocusPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, oldFocus), oldFocus)); }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        ExhaustiveVariable.setBaseValue(this, this.magicNumber);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { return new Ignis(); }
}