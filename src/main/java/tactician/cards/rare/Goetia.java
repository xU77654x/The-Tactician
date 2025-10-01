package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician8DarkCard;
import tactician.character.TacticianRobin;
import tactician.effects.PlayVoiceEffect;
import tactician.effects.cards.TacticianDarkEffect;
import tactician.powers.weapons.Weapon8DarkPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Goetia extends Tactician8DarkCard {
    public static final String ID = makeID(Goetia.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Goetia() {
        super(ID, info);
        setDamage(8, 3);
        setBlock(6, 2);
        tags.add(CustomTags.DARK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new PlaySoundAction("tactician:Goetia", 1.00f));
        AbstractDungeon.effectList.add(new PlayVoiceEffect("CA_MiscMagic"));
        calculateCardDamage(m);
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new VFXAction(new TacticianDarkEffect(m.hb.cX, m.hb.cY, null, 1.00F, 50F, 4)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int realDamage = baseDamage;
        int realBlock = baseBlock;
        baseDamage += Wiz.playerWeaponCalc(m, 9);
        baseBlock += Wiz.playerWeaponCalc(m, 9);
        super.calculateCardDamage(m);
        baseDamage = realDamage;
        baseBlock = realBlock;
        this.isDamageModified = (damage != baseDamage);
        this.isBlockModified = (block != baseBlock);
    }

    @Override
    public AbstractCard makeCopy() { return new Goetia(); }
}