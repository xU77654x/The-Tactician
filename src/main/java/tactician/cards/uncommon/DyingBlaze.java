package tactician.cards.uncommon;

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
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import tactician.actions.MakeAndExhaustCopyAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician6FireCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.powers.weapons.Weapon6FirePower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class DyingBlaze extends Tactician6FireCard {
    public static final String ID = makeID(DyingBlaze.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public DyingBlaze() {
        super(ID, info);
        setDamage(8, 3);
        setMagic(2, 1);
        tags.add(CustomTags.FIRE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new PlayVoiceEffect("CA_MiscMagic")));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); }
        calculateCardDamage(m);
        addToBot(new VFXAction(p, new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.00F));
        addToBot(new PlaySoundAction("tactician:DyingBlaze", 1.00f));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new WaitAction(1.0F));
        addToBot(new MakeAndExhaustCopyAction(makeStatEquivalentCopy(), this.magicNumber));

        /*
        for (int i = this.magicNumber; i > 0; i--) {
            addToBot(new MakeAndExhaustCopyAction(makeStatEquivalentCopy(), 1));
        } */

        /*
        for (int x = 0; x < 3; x++) {
            for (int i = this.magicNumber; i > 0; i--) {
                addToBot(new MakeTempCardInExhaustAction(makeStatEquivalentCopy(), 1));
                x += 1;
            }
            addToBot(new WaitAction(1));
            x = 0;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
         */// This is supposed to create a pause every 3 exhausted copy, but instead causes the game to hang due to the UnlockTracker already having seen this card.
    }

    @Override
    public void applyPowers() {
        int realDamage = baseDamage;
        baseDamage += AbstractDungeon.player.exhaustPile.size();
        super.applyPowers();
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        baseDamage += AbstractDungeon.player.exhaustPile.size();
        baseDamage += Wiz.playerWeaponCalc(m, 6);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        this.isDamageModified = (damage != baseDamage);
    }

    @Override
    public AbstractCard makeCopy() { return new DyingBlaze(); }
}