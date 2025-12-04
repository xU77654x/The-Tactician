package tactician.cards.additional;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician9CopyCard;
import tactician.character.TacticianRobin;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;

public class Glacies extends Tactician9CopyCard {
	public static final String ID = makeID(Glacies.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.RARE,
			CardTarget.ENEMY,
			2
	);

	public Glacies() {
		super(ID, info);
		setBlock(11, 4);
		setMagic(2, 1);
		tags.add(CustomTags.COPY);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		calculateCardDamage(m);
		AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Wiz.getCopyColor()));
		addToBot(new PlaySoundAction("tactician:Glacies", 1.10F));
		addToBot(new GainBlockAction(p, p, this.block));

		for (int i = this.magicNumber; i > 0; i--) {
			if (Wiz.playerWeaponCalc(m, 9) > 0) {
				if (!AbstractDungeon.player.orbs.isEmpty()) {
					for (AbstractOrb o : AbstractDungeon.player.orbs) {
						o.onStartOfTurn();
						o.onEndOfTurn();
					}
				}
			}
			else {
				if (!AbstractDungeon.player.orbs.isEmpty()) {
					(AbstractDungeon.player.orbs.get(0)).onStartOfTurn();
					(AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
				}
			}
		}
	}

	@Override
	public void calculateCardDamage(AbstractMonster m) {
		int realBlock = baseBlock;
		baseBlock += Wiz.playerWeaponCalc(m, 9);
		super.calculateCardDamage(m);
		baseBlock = realBlock;
		this.isBlockModified = (block != baseBlock);
	}

	@Override
	public AbstractCard makeCopy() { return new Glacies(); }
}