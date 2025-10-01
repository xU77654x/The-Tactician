package tactician.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.FlashSparrowAction;
import tactician.cards.Tactician9CopyCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class FlashSparrow extends Tactician9CopyCard {
    public static final String ID = makeID(FlashSparrow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public FlashSparrow() {
        super(ID, info);
        setDamage(5, 3);
        tags.add(CustomTags.COPY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new FlashSparrowAction(p, m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL))); }

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
    public AbstractCard makeCopy() { return new FlashSparrow(); }
}