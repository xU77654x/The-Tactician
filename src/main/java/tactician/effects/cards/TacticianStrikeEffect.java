package tactician.effects.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import tactician.actions.PlaySoundAction;
import tactician.util.Wiz;

public class TacticianStrikeEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final AbstractMonster m;

	public TacticianStrikeEffect(AbstractMonster m, float x, float y, Color color) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.color = color;
		this.startingDuration = 0.0F;
	}

	@Override
	public void update() {
		AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y, 0.0F, 0.0F, 315.0F, 2.0F, this.color, Color.LIGHT_GRAY.cpy()));
		if ( Wiz.playerWeaponCalc(m, 9) > 0) { AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Strike_Strong", 1.25f)); }
		else if ( Wiz.playerWeaponCalc(m, 9) < 0) { AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Strike_Weak", 1.10f)); }
		else { AbstractDungeon.actionManager.addToTop(new PlaySoundAction("tactician:Strike_Neutral", 1.20f)); }
		this.isDone = true;
	}

	@Override
	public void render(SpriteBatch sb) {}

	@Override
	public void dispose() {}
}