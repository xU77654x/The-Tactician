package tactician.cards.common;

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
import tactician.effects.cards.Tactician1SwordEffect;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.Weapon1SwordPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class WrathStrike extends Tactician1SwordCard {
    public static final String ID = makeID(WrathStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public WrathStrike() {
        super(ID, info);
        setDamage(5, 1);
        setMagic(4, 2);
        tags.add(CardTags.STRIKE);
        tags.add(CustomTags.SWORD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); }
        AbstractDungeon.effectList.add(new PlayVoiceEffect("CA_Sword"));
        calculateCardDamage(m);
        addToBot(new VFXAction(new Tactician1SwordEffect(m.hb.cX, m.hb.cY, "tactician:WrathStrike", 1.33F, 195.0F, 0F, 0F, 3.5F, Color.SCARLET), 0.00F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(this.magicNumber), this.magicNumber));
    }

    @Override
    public void applyPowers() {
        int realDamage = baseDamage;
        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseDamage += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.applyPowers();
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += Wiz.playerWeaponCalc(m, 1);

        if (AbstractDungeon.player.hasPower(DeflectPower.POWER_ID))
            baseDamage += AbstractDungeon.player.getPower(DeflectPower.POWER_ID).amount;
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new WrathStrike(); }
}