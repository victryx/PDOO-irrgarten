# encoding: utf-8
require_relative 'dice'
require_relative 'player'
require_relative 'labyrinth'
require_relative 'monster'
require_relative 'directions'
require_relative 'orientation'
require_relative 'game_state'

module Irrgarten
  class Game
    @@MAX_ROUNDS = 10

    def initialize(n_players)
      @players = []
      @monsters = []
      @log = ""

      (0...n_players).each do |i|
        @players << Player.new(i.to_s, Dice.random_intelligence, Dice.random_strength) # << = @players.push = @players.append
      end

      @current_player_index = Dice.who_starts(n_players)
      @current_player = @players[@current_player_index]
      @log = ""

      @labyrinth = Labyrinth.new(10, 10, 0, 0)
      configure_labyrinth
      @labyrinth.spread_players(@players)
    end

    def finished
      @labyrinth.have_a_winner
    end

    def next_step(preferred_direction)
      if !@current_player.dead
        direction = actual_direction(preferred_direction)
        if direction != preferred_direction
          log_player_no_orders
        end

        puts direction
        monster = @labyrinth.put_player(direction, @current_player)

        if monster.nil?
          log_no_monster
        else
          winner = combat(monster)
          manage_reward(winner)
        end
      else
        manage_resurrection
      end

      end_game = finished

      unless end_game
        next_player
      end

      end_game
    end

    def game_state
      GameState.new(
        @labyrinth.to_s,
        @players.to_s,
        @monsters.to_s,
        @current_player_index,
        finished,
        @log
      )
    end

    private

    def configure_labyrinth
      m1 = Monster.new("bicho mostro", Dice.random_intelligence, Dice.random_strength)
      @monsters.append(m1)
      @labyrinth.add_monster(1, 0, m1)
      m2 = Monster.new("mostro bicho", Dice.random_intelligence, Dice.random_strength)
      @monsters.append(m2)
      @labyrinth.add_monster(2, 1, m2)
    end

    def next_player
      @current_player_index = (@current_player_index + 1) % @players.size
      @current_player = @players[@current_player_index]
    end

    def actual_direction(preferred_direction)
      current_row = @current_player.row
      current_col = @current_player.col
      valid_moves = @labyrinth.valid_moves(current_row, current_col)

      @current_player.move(preferred_direction, valid_moves)
    end

    def combat(monster)
      rounds = 0
      winner = GameCharacter::PLAYER
      player_attack = @current_player.attack
      lose = monster.defend(player_attack)

      while !lose && rounds < @@MAX_ROUNDS
        winner = GameCharacter::MONSTER
        rounds += 1
        monster_attack = monster.attack
        lose = @current_player.defend(monster_attack)

        if !lose
          player_attack = @current_player.attack
          winner = GameCharacter::PLAYER
          lose = monster.defend(player_attack)
        end
      end

      self.log_rounds(rounds, @@MAX_ROUNDS)
      winner
    end

    def manage_reward(winner)
      if winner == GameCharacter::PLAYER
        @current_player.receive_reward
        log_player_won
      else
        log_monster_won
      end
    end

    def manage_resurrection
      if Dice.resurrect_player
        @current_player.resurrect
        log_resurrected
      else
        log_player_skip_turn
      end
    end

    def log_player_won
      @log += "Player #{@current_player} won\n"
    end

    def log_monster_won
      @log += "Monster won the combat\n"
    end

    def log_resurrected
      @log += "Player #{@current_player} resurrected\n"
    end

    def log_player_skip_turn
      @log += "Player #{@current_player} skip turn\n"
    end

    def log_player_no_orders
      @log += "Player #{@current_player} didn't follow human player instructions\n"
    end

    def log_no_monster
      @log += "#{@current_player} empty cell or unable to move\n"
    end

    def log_rounds(rounds, max)
      @log += "Rounds: #{rounds} of #{max}\n"
    end
  end
end