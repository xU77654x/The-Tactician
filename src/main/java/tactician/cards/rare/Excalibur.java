package tactician.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician5WindCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.TacticianWindEffect;
import tactician.powers.MaxHandSizePower;
import tactician.powers.weapons.Weapon5WindPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Excalibur extends Tactician5WindCard {
    public static final String ID = makeID(Excalibur.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Excalibur() {
        super(ID, info);
        setDamage(10, 5);
        setMagic(1, 1);
        tags.add(CustomTags.WIND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:Excalibur_Cast", 1.33f));
        addToBot(new VFXAction(new PlayVoiceEffect("CA_MiscMagic")));
        calculateCardDamage(m);
        addToBot(new VFXAction(new TacticianWindEffect(p.hb.cX, p.hb.cY, null, 1.33f, Color.TEAL.cpy(), 1.0F)));
        addToBot(new WaitAction(0.05F));
        addToBot(new VFXAction(new TacticianWindEffect(p.hb.cX, p.hb.cY, null, 1.33f, Color.TEAL.cpy(), 1.0F)));
        addToBot(new WaitAction(0.05F));
        addToBot(new VFXAction(new TacticianWindEffect(p.hb.cX, p.hb.cY, null, 1.33f, Color.TEAL.cpy(), 1.0F)));
        addToBot(new WaitAction(0.05F));
        addToBot(new VFXAction(new TacticianWindEffect(p.hb.cX, p.hb.cY, "tactician:Excalibur_Hit", 1.33f, Color.TEAL.cpy(), 1.0F)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

        addToBot(new ApplyPowerAction(p, p, new DrawPower(p, 1), 1));
        if (Wiz.playerWeaponCalc(m, 9) > 0) { addToBot(new ApplyPowerAction(p, p, new MaxHandSizePower(this.magicNumber), this.magicNumber)); }
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 9);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new Excalibur(); }
}