import pygame
import random
import time
pygame.font.init()

WIDTH, HEIGHT = 900, 500
PADDLE_WIDTH = 20
PADDLE_HEIGHT = 70
BALL_DIAMETER = 15
game_font = pygame.font.SysFont('Arial', 35)

SCREEN = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Ping - Pong")

player1_score = 0
player2_score = 0

class Paddle(pygame.Rect):
    def __init__(self, x, y, width, height, colour) -> None:
        super().__init__(x, y, width, height)
        self.colour = colour
    
    def draw(self):
        pygame.draw.rect(SCREEN, self.colour, self)
   
class Ball(pygame.Rect):
    def __init__(self, x, y, diameter) -> None:
        super().__init__(x, y, diameter, diameter)
        self.x = x
        self.y = y
        self.x_vel = 3
        self.y_vel = 3
        self.direction = random.choice([-1, 1])
        self.diameter = diameter
    
    def draw(self):
        pygame.draw.circle(SCREEN, "white", (self.x, self.y), int(self.diameter/2))

    def move(self):
        self.x += (self.x_vel * self.direction)
        self.y += (self.y_vel * self.direction)
        if (self.y - (self.diameter/2)) < 0 or self.y > HEIGHT - self.diameter:
            self.y_vel *= -1
        
        

def draw_things(paddle1, paddle2, ball):
    img1 = game_font.render(f"{player1_score}", True, "white")
    img2 = game_font.render(f"{player2_score}", True, "white")
    SCREEN.blit(img1, ((WIDTH//4) - (img1.get_width()//2),0))
    SCREEN.blit(img2, ((WIDTH*(3/4)) - (img1.get_width()//2),0))
    pygame.draw.line(SCREEN, "WHITE", ((WIDTH//2), 0), ((WIDTH//2), HEIGHT))
    paddle1.draw()
    paddle2.draw()
    ball.draw()
    pygame.display.flip()

def handle_movement(paddle1, paddle2, ball):
    global player1_score, player2_score
    keys = pygame.key.get_pressed()
    ball.move()
    if keys[pygame.K_w] and paddle1.y > 0:
        paddle1.y -= 5
    elif keys[pygame.K_s] and paddle1.y < HEIGHT - PADDLE_HEIGHT:
        paddle1.y += 5

    if keys[pygame.K_UP] and paddle2.y > 0:
        paddle2.y -= 5
    elif keys[pygame.K_DOWN] and paddle2.y < HEIGHT - PADDLE_HEIGHT:
        paddle2.y += 5

    if (ball.x - (ball.diameter//2) <= paddle1.right and (ball.y >= paddle1.y and ball.y < paddle1.y + PADDLE_HEIGHT)) or (ball.x + (ball.diameter//2) >= paddle2.left and (ball.y >= paddle2.y and ball.y < paddle2.y + PADDLE_HEIGHT)):
        ball.x_vel += 1
        ball.x_vel *= -1
        

    if ball.x - (ball.diameter//2) <= 0:
        player2_score += 1
        time.sleep(1.5)
        reset_game_objects()
    
    elif ball.x + (ball.diameter//2) >= WIDTH:
        player1_score += 1
        time.sleep(1.5)
        reset_game_objects()

def reset_game_objects():
    global paddle1, paddle2, ball
    paddle1 = Paddle(10, int((HEIGHT/2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, "red")
    paddle2 = Paddle(870, int((HEIGHT/2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, "blue")
    ball = Ball(int(WIDTH/2), int(HEIGHT/2), BALL_DIAMETER)



paddle1 = Paddle(10, int((HEIGHT/2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, "red")
paddle2 = Paddle(870, int((HEIGHT/2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, "blue")
ball = Ball(int(WIDTH/2), int(HEIGHT/2), BALL_DIAMETER)
clock = pygame.time.Clock()
running = True

while running:
    clock.tick(60)
    SCREEN.fill("black")
    draw_things(paddle1, paddle2, ball)
    handle_movement(paddle1, paddle2, ball)
   

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    

pygame.quit()