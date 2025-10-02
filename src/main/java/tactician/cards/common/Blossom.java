package tactician.cards.common;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;

public class Blossom extends TacticianCard {
    public static final String ID = makeID(Blossom.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public Blossom() {
        super(ID, info);
        setMagic(1, 1);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:Blossom", 0.80f));
        addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 1, false), 1));
        addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));

    }

    protected Texture getPortraitImage() {
        if (this.upgraded) { return ImageMaster.loadImage("tactician/images/cards/skill/BlossomHero_p.png"); }
        return super.getPortraitImage();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgraded = true;
            upgradeName();
            upgradeMagicNumber(1);
            initializeTitle();
            initializeDescription();
            loadCardImage("tactician/images/cards/skill/BlossomHero.png");
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Blossom(); }
}