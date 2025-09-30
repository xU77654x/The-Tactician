package tactician.cards.rare;

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
import tactician.effects.cards.Tactician1SwordLanceEffect;
import tactician.powers.weapons.Weapon1SwordPower;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Astra extends Tactician1SwordCard {
    public static final String ID = makeID(Astra.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Astra() {
        super(ID, info);
        setDamage(3, 0);
        setExhaust(true, false);
        tags.add(CustomTags.SWORD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new PlayVoiceEffect("CA_Sword"));
        calculateCardDamage(m);
        addToBot(new VFXAction(new Tactician1SwordLanceEffect(m.hb.cX + 40, m.hb.cY + 30, "tactician:Astra_Hit1", 1.25F, 198.0F, 0F, 0F, 2.5F, Color.YELLOW), 0.00F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new VFXAction(new Tactician1SwordLanceEffect(m.hb.cX - 20, m.hb.cY - 50, "tactician:Astra_Hit2", 1.25F, 54.0F, 0F, 0F, 2.5F, Color.GREEN), 0.00F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new VFXAction(new Tactician1SwordLanceEffect(m.hb.cX, m.hb.cY + 30, "tactician:Astra_Hit3", 1.25F, 270.0F, 0F, 0F, 2.5F, Color.BLUE), 0.00F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new VFXAction(new Tactician1SwordLanceEffect(m.hb.cX + 20, m.hb.cY - 50, "tactician:Astra_Hit4", 1.25F, 124.0F, 0F, 0F, 2.25F, Color.PURPLE), 0.00F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new VFXAction(new Tactician1SwordLanceEffect(m.hb.cX - 40, m.hb.cY + 30, "tactician:Astra_Hit5", 1.25F, 342.0F, 0F, 0F, 3.5F, Color.RED), 0.00F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        if (AbstractDungeon.player instanceof TacticianRobin && !p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); }
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
    public AbstractCard makeCopy() { return new Astra(); }
}