# encoding: utf-8
require_relative 'directions'

module Irrgarten
  class Labyrinth
    @@BLOCK_CHAR = 'X'
    @@EMPTY_CHAR = '-'
    @@MONSTER_CHAR = 'M'
    @@COMBAT_CHAR = 'C'
    @@EXIT_CHAR = 'E'
    @@ROW = 0
    @@COL = 1

    def initialize(nRows, nCols, exitRow, exitCol)
      @n_rows = nRows
      @n_cols = nCols
      @exit_row = exitRow
      @exit_col = exitCol

      @players = Array.new(@n_rows) { Array.new(@n_cols) }
      @monsters = Array.new(@n_rows) { Array.new(@n_cols) }
      @labyrinth = Array.new(@n_rows) { Array.new(@n_cols, @@EMPTY_CHAR) }

      @labyrinth[@exit_row][@exit_col] = @@EXIT_CHAR
    end

    def spread_players(players)

    end

    def have_a_winner
      @players[@exit_row][@exit_col] != nil
    end

    def to_s
      str = ""

      (0...@n_rows).each do |i|
        (0...@n_cols).each do |j|
          str += @labyrinth[i][j].to_s + " "
        end
        str += "\n"
      end

      str
    end

    def add_monster(row, col, monster)
      if pos_ok(row, col) && empty_pos(row, col)
        @monsters[row][col] = monster
        monster.set_pos(row,col)
      end
    end

    def put_player(direction, player)

    end

    def add_block(orientation, start_row, start_col, length)

    end

    def valid_moves(row, col)

    end

    private

    def pos_ok(row, col)
      row.between?(0, @n_rows - 1) && col.between?(0, @n_cols - 1)
    end

    def empty_pos(row, col)
      @labyrinth[row][col] == @@EMPTY_CHAR
    end

    def monster_pos(row, col)
      @labyrinth[row][col] == @@MONSTER_CHAR
    end

    def exit_pos(row, col)
      @labyrinth[row][col] == @@EXIT_CHAR
    end

    def combat_pos(row, col)
      @labyrinth[row][col] == @@COMBAT_CHAR
    end

    def can_step_on(row, col)
      pos_ok(row, col) && !combat_pos(row, col) && @labyrinth[row][col] != @@BLOCK_CHAR
    end

    def update_old_pos(row, col)
      if pos_ok(row, col)
        @labyrinth[row][col] = combat_pos(row, col) ? @@MONSTER_CHAR : @@EMPTY_CHAR
      end
    end

    def dir_2_pos(row, col, directions)
      out = [row, col]
      case directions
      when Directions::DOWN
        out[@@ROW] += 1
      when Directions::UP
        out[@@ROW] -= 1
      when Directions::LEFT
        out[@@COL] += 1
      when Directions::LEFT
        out[@@COL] -= 1
      end
      zz      out
    end

    def random_empty_pos()
      row, col = 0
      loop do
        row = Dice.random_pos(@n_rows)
        col = Dice.random_pos(@n_cols)
        break unless empty_pos(row, col)
      end
      [row, col]
    end

    def put_player_2d(old_row, old_col, row, col, player)

    end

  end
end
