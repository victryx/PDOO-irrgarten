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
      players.each do |player|
        pos = random_empty_pos
        put_player_2d(-1, -1, pos[@@ROW], pos[@@COL], player)
      end
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
        monster.set_pos(row, col)
      end
    end

    def put_player(direction, player)
      old_row = player.row
      old_col = player.col
      new_pos = dir_2_pos(old_row, old_col, direction)
      put_player_2d(old_row, old_col, new_pos[@@ROW], new_pos[@@COL], player)
    end

    def add_block(orientation, start_row, start_col, length)
      inc_row = 0
      inc_col = 1
      if orientation == Orientation::VERTICAL
        inc_row = 1
        inc_col = 0
      end
      row = start_row
      col = start_col

      while pos_ok(row, col) && empty_pos(row, col) && length > 0
        @labyrinth[row][col] = @@BLOCK_CHAR
        length -= 1
        row += inc_row
        col += inc_col
      end
    end

    def valid_moves(row, col)
      output = []
      if can_step_on(row + 1, col)
        output.push(Directions::DOWN)
      end
      if can_step_on(row - 1, col)
        output.push(Directions::UP)
      end
      if can_step_on(row, col + 1)
        output.push(Directions::RIGHT)
      end
      if can_step_on(row, col - 1)
        output.push(Directions::LEFT)
      end

      output
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
        out[@@COL] -= 1
      when Directions::RIGHT
        out[@@COL] += 1
      end
      out
    end

    def random_empty_pos
      row = 0
      col = 0
      loop do
        row = Dice.random_pos(@n_rows)
        col = Dice.random_pos(@n_cols)
        break if empty_pos(row, col)
      end
      [row, col]
    end

    def put_player_2d(old_row, old_col, row, col, player)
      output = nil
      if can_step_on(row, col)
        if pos_ok(old_row, old_col) && @players[old_row][old_col] == player
          update_old_pos(old_row, old_col)
          @players[old_row][old_col] = nil
        end

        if monster_pos(row, col)
          @labyrinth[row][col] = @@COMBAT_CHAR;
          output = @monsters
        else
          @labyrinth[row][col] = player.number.to_s
        end
        @players[row][col] = player
        player.set_pos(row, col)
      end

      output
    end
  end
end

