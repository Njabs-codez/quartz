import pygame, sys
import random

WIDTH, HEIGHT = 400, 650
FPS = 60
GRAVITY = 10
ACCELERATION = 0.125
PIPE_WIDTH = 55
PIPE_GAP = 150
PIPE_SPACE = 247
pygame.font.init()

screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("FLAPPY BIRD")

font = pygame.font.SysFont("Arial", 50, True)
score_font = pygame.font.SysFont("Arial", 100, True)
score = 0

class Bird(pygame.Rect):
    def __init__(self, x, y, width, height):
        super().__init__(x, y, width, height)
        self.y_vel = 0
        self.jump_vel = -3
        self.alive = True

    def draw(self):
        pygame.draw.rect(screen, "yellow", self)

    def move(self, jump = False):
        self.y_vel += ACCELERATION
        self.y += min(self.y_vel, GRAVITY)
        if jump:
            self.y_vel = self.jump_vel



class Pipes:
    def __init__(self, x) -> None:
       self.x = x
       self.gap = PIPE_GAP
       self.width = PIPE_WIDTH
       self.bottom_height = random.randrange(60, 390)
       self.y_bottom = HEIGHT - self.bottom_height
       self.top_height = self.y_bottom - self.gap
       self.y_up = 0
       self.vel = -2
       self.passed = 0
    
       self.top_rect = pygame.Rect(self.x, self.y_up, self.width, self.top_height)
       self.bottom_rect = pygame.Rect(self.x, self.y_bottom, self.width, self.bottom_height)

    def draw(self):
        pygame.draw.rect(screen, "green", self.top_rect)  
        pygame.draw.rect(screen, "green", self.bottom_rect)

    def move(self):
        self.top_rect.move_ip(self.vel, 0)
        self.bottom_rect.move_ip(self.vel, 0)
        

def check_collisions(bird, tubes):
    for tube in tubes:
        if tube.top_rect.colliderect(bird) or tube.bottom_rect.colliderect(bird):
            # print("collide")
            bird.alive = False
     
def game_over(tubes, bird, font, screen):
    for tube in tubes:
        tube.vel = 0
    bird.y_vel = 0
    game_over_text = font.render("GAME OVER", True, "red")
    screen.blit(game_over_text, (((WIDTH//2)-(game_over_text.get_width()//2)),(HEIGHT//2) - 25))




running = True
larry = Bird(170, 300, 25, 25)
tubes = []
for i in range(4):
    tubes.append(Pipes((WIDTH-PIPE_WIDTH)+(PIPE_SPACE*i)))
    
# pipes = Pipes(WIDTH - PIPE_WIDTH)
clock = pygame.time.Clock()
start_time = pygame.time.get_ticks()
while running:
    score_text = score_font.render(f"{score}", True, "white")
    clock.tick(FPS)
    screen.fill("black")
    screen.blit(score_text, (((WIDTH//2)-(score_text.get_width()//2)),(HEIGHT//2) - 50))


    for pipe in tubes:
        pipe.draw()
        pipe.move()
    
    for i in range(3,-1,-1):
        if (tubes[i].top_rect.x + tubes[i].width) < 0:
            tubes.remove(tubes[i])
            tubes.append(Pipes(tubes[-1].top_rect.x + PIPE_SPACE))

    # for i in range(len(tubes)):
    #     if larry.left == tubes[i].top_rect.right or larry.left == tubes[i].bottom_rect.right:
    #         score += 1

    #     elif larry.left == tubes[i + 1].top_rect.right or larry.left == tubes[i + 1].bottom_rect.right:
    #         score += 1
    current = pygame.time.get_ticks()
    if current - start_time >= 1000:
        score += 1
        start_time = current

    print(tubes[1].top_rect.right)        
    
    larry.draw()
    larry.move()
    
    # for tube in tubes:
    #     if larry.left >= tube.top_rect.right or larry.left >= tube.bottom_rect.right:
    #         score += 1

    
    check_collisions(larry, tubes)
    if larry.alive == False:
        game_over(tubes, larry, font, screen)
   
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
            
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE and larry.alive == True:
                larry.move(jump=True)
           
    
    
    pygame.display.flip()
                

