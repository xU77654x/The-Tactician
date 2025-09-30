package tactician.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician6FireCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.arcfire.TacticianFireballEffect;
import tactician.powers.LoseFocusPower;
import tactician.powers.weapons.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Arcfire extends Tactician6FireCard {
    public static final String ID = makeID(Arcfire.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public Arcfire() {
        super(ID, info);
        setDamage(8, 2);
        setMagic(2, 1);
        tags.add(CustomTags.FIRE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new PlaySoundAction("tactician:Arcfire_Cast", 1.33f));
        AbstractDungeon.effectList.add(new PlayVoiceEffect("Arcfire"));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); }
        calculateCardDamage(m);
        addToBot(new VFXAction(p, new TacticianFireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.50F));
        addToBot(new PlaySoundAction("tactician:Arcfire_Hit", 1.33f));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(this.magicNumber), this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 6);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new Arcfire(); }
}