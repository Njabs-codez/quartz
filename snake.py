import pygame
import random
pygame.font.init()

SCREEN_WIDTH, SCREEN_HEIGHT = 500, 500
UNIT_SIZE = 25
BODY_PARTS = 4
score = 0

screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
pygame.display.set_caption("SNAKE IN PYTHON")

class Food:
    def __init__(self) -> None:
        self.x = random.randint(0, int(SCREEN_WIDTH/UNIT_SIZE)-1)*UNIT_SIZE
        self.y = random.randint(0, int(SCREEN_HEIGHT/UNIT_SIZE)-1)*UNIT_SIZE
        self.rect = pygame.Rect(self.x, self.y, UNIT_SIZE, UNIT_SIZE)

    def draw(self):
        pygame.draw.rect(screen, "red", self.rect)

    def reset(self):
        self.x = random.randint(0, int(SCREEN_WIDTH/UNIT_SIZE)-1)*UNIT_SIZE
        self.y = random.randint(0, int(SCREEN_HEIGHT/UNIT_SIZE)-1)*UNIT_SIZE
        self.rect = pygame.Rect(self.x, self.y, UNIT_SIZE, UNIT_SIZE)


class Snake:
    DIRECTION = "D"

    def __init__(self) -> None:
        self.alive = True
        self.coordinates = []
        self.squares = []
        for _ in range(BODY_PARTS):
            self.coordinates.append([0,0])

        for obj in self.coordinates:
            square = pygame.Rect(obj[0], obj[1], UNIT_SIZE, UNIT_SIZE)
            self.squares.append(square)

    def draw(self):
        head = 1
        for square in self.squares:
            pygame.draw.rect(screen, "green", square)
            # if head == 1:
            #     pygame.draw.rect(screen, "blue", square)
            #     head = 2
            # else:
            #     pygame.draw.rect(screen, "green", square)
    
    def move(self, food):
        if self.alive:
            x, y = self.coordinates[0] 
                        
            if self.DIRECTION == "U":
                y -= UNIT_SIZE
            elif self.DIRECTION == "D":
                y += UNIT_SIZE
            elif self.DIRECTION == "L":
                x -= UNIT_SIZE
            elif self.DIRECTION == "R":
                x += UNIT_SIZE

            head = [x, y]
            self.coordinates.insert(0, head)

            head_square = pygame.Rect(head[0], head[1], UNIT_SIZE, UNIT_SIZE)
            self.squares.insert(0, head_square)
            if self.squares[0].colliderect(food.rect):
                global score
                score += 1
                food.reset()
            else:
                del self.coordinates[-1]
                del self.squares[-1]


def check_collisions(snake):
    if snake.squares[0].x < 0 or snake.squares[0].x >=  SCREEN_WIDTH:
        snake.alive = False
    if snake.squares[0].y < 0 or snake.squares[0].y >=  SCREEN_HEIGHT:
        snake.alive = False
    for i in range(1, len(snake.squares)):
        if snake.squares[0].x == snake.squares[i].x and snake.squares[0].y == snake.squares[i].y:
            snake.alive = False

def game_over():
    gm_font = pygame.font.SysFont("MV Boli", 50)
    img = gm_font.render("GAME OVER", True, "red")
    screen.blit(img, (int((SCREEN_WIDTH/2)-(img.get_width()/2)), int((SCREEN_HEIGHT/2)-25)))

def draw_objects(snake, food):
    snake.draw()
    food.draw()
    font = pygame.font.SysFont("mv boli", 20)
    img = font.render(f"Score: {score}", True, "blue")
    screen.blit(img, (0, 10))
    

snake = Snake()

food = Food()
running = True
pygame.time.set_timer(pygame.USEREVENT, 115)
while running:
    screen.fill("black")
    
    if snake.alive:
        draw_objects(snake, food)
    
    else:
        game_over()
    
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        if event.type == pygame.USEREVENT:
            snake.move(food)
            check_collisions(snake)
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_UP and snake.DIRECTION != "D":
               snake.DIRECTION = "U"
            elif event.key == pygame.K_DOWN and snake.DIRECTION != "U":
               snake.DIRECTION = "D"
            elif event.key == pygame.K_LEFT and snake.DIRECTION != "R":
               snake.DIRECTION = "L"
            elif event.key == pygame.K_RIGHT and snake.DIRECTION != "L":
               snake.DIRECTION = "R"
    
    
    

    pygame.display.update()

pygame.quit()