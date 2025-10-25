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

      (0..n_players).each do |i|
        @players << Player.new(i.to_s, Dice.random_intelligence, Dice.random_strength) # << = @players.push = @players.append
      end

      @current_player_index = Dice.who_starts(n_players)
      @current_player = @players[@current_player_index]
      @log = ""

      @labyrinth = Labyrinth.new(10, 10, 0, 0)
      configure_labyrinth
    end

    def finished
      @labyrinth.have_a_winner
    end

    def next_step(preferred_direction)

    end

    def get_game_state
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

    end

    def combat(monster)

    end

    def manage_reward(winner)

    end

    def manage_resurrection(winner)

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
