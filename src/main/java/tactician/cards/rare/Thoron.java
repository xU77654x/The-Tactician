package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician7ThunderCard;
import tactician.cards.other.Hex;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.ThoronEffect;
import tactician.powers.weapons.Weapon7ThunderPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Thoron extends Tactician7ThunderCard {
    public static final String ID = makeID(Thoron.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    public Thoron() {
        super(ID, info);
        setDamage(10, 3);
        tags.add(CustomTags.THUNDER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new PlayVoiceEffect("Thoron"));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            addToBot(new VFXAction(p, new ThoronEffect("tactician:Thoron_Cast", 1.50f), 0.01F));
            for (AbstractMonster mo : (AbstractDungeon.getMonsters()).monsters) {
                calculateCardDamage(mo);
                addToBot(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                addToBot(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            }
            if (this.upgraded) { addToBot(new MakeTempCardInHandAction(new Hex(), 1)); }
            else { addToBot(new MakeTempCardInDrawPileAction(new Hex(), 1, true, true)); }
        }
        addToBot(new PlaySoundAction("tactician:Thoron_Glint", 1.10f));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (m != null) {
            int realDamage = baseDamage;
            baseDamage += Wiz.playerWeaponCalc(m, 9);
            super.calculateCardDamage(m);
            baseDamage = realDamage;
            this.isDamageModified = (damage != baseDamage);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Thoron(); }
}