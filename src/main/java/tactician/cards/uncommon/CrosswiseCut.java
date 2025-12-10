package tactician.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.Tactician1SwordCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.TacticianSwordLanceEffect;
import tactician.powers.weapons.Weapon1SwordPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class CrosswiseCut extends Tactician1SwordCard {
    public static final String ID = makeID(CrosswiseCut.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public CrosswiseCut() {
        super(ID, info);
        setDamage(4, 2);
        tags.add(CustomTags.SWORD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new PlayVoiceEffect("CA_Sword")));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); }
        calculateCardDamage(m);
        addToBot(new VFXAction(new TacticianSwordLanceEffect(m.hb.cX, m.hb.cY, "tactician:CrosswiseCut1", 1.875F, 135.0F, -375.0F, -375F, 3.0F, Color.ROYAL.cpy()), 0.00F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        boolean even = true;
        for (int i = Wiz.countDebuffs(p); i > 0; i--) {
            if (even) { addToBot(new VFXAction(new TacticianSwordLanceEffect(m.hb.cX, m.hb.cY, "tactician:CrosswiseCut2", 1.875F, 225.0F, 375.0F, -375F, 3.0F, Color.ROYAL.cpy()), 0.00F)); }
            else { addToBot(new VFXAction(new TacticianSwordLanceEffect(m.hb.cX, m.hb.cY, "tactician:CrosswiseCut1", 1.875F, 135.0F, -375.0F, -375F, 3.0F, Color.ROYAL.cpy()), 0.00F)); }
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            even = !even;
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void triggerOnExhaust() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int debuffs = Wiz.countDebuffs(AbstractDungeon.player);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + debuffs;
        if (debuffs == 1) { this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1]; }
        else { this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[2]; }
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 1);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new CrosswiseCut(); }
}