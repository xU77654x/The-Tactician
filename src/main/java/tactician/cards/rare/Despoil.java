package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.Elixir;
import com.megacrit.cardcrawl.potions.SpeedPotion;
import com.megacrit.cardcrawl.potions.SteroidPotion;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.potions.SagePotion;
import tactician.util.CardStats;
import tactician.util.Wiz;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.potionRng;

public class Despoil extends TacticianCard {
    public static final String ID = makeID(Despoil.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Despoil() {
        super(ID, info);
        setCostUpgrade(0);
        setExhaust(true);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPotion potion = new Elixir();
        int roll = potionRng.random(0, 2);
        switch (roll) {
            case 0: potion = new SteroidPotion(); break;
            case 1: potion = new SpeedPotion(); break;
            case 2: potion = new SagePotion(); break;
        }

        m = Wiz.getRandomEnemy();
        addToBot(new PlaySoundAction("tactician:Despoil", 1.25f)); // TODO: Don't play the sound if all potion slots are full.
        for (int i = 0; i < 20; i++) { AbstractDungeon.effectList.add(new GainPennyEffect(p, m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, false)); }

        addToBot(new ObtainPotionAction(potion));

    }

    @Override
    public AbstractCard makeCopy() { return new Despoil(); }
}