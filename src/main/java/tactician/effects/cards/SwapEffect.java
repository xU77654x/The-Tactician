package tactician.effects.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import tactician.actions.PlaySoundAction;

public class SwapEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final AbstractMonster m;
	private final String soundKey;

	public SwapEffect(AbstractMonster m, float x, float y, String soundKey, Color color) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.color = color;
		this.soundKey = soundKey;
		this.startingDuration = 0.0F;
	}

	@Override
	public void update() {
		AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y, 0.0F, 0.0F, 315.0F, 4.0F, this.color, Color.LIGHT_GRAY.cpy()));
		AbstractDungeon.actionManager.addToTop(new PlaySoundAction(soundKey, 1.10f));
		this.isDone = true;
	}

	@Override
	public void render(SpriteBatch sb) {}

	@Override
	public void dispose() {}
}