/**
 * The Book class implements Book
 *
 * @author Hoang Nguyen Minh
 */
class Book {

	String bookName; // the book name
	int valueTag; // an integer between -100 and 100
	Library library; // the library having this book it its inventory
	RentSettings rs; // rent settings

	public Book(String bookName, int valueTag) {
		this.bookName = bookName;
		this.valueTag = valueTag;
	}

	// set the rent dates; if dates are not valid catch DateFormatException and
	// return false,
	// if rentDate > dueDate catch RentPeriodException and return false
	// if one the exceptions occur there is no RentSettings object
	public boolean rentBook(String rentDate, String dueDate, Library library) {
		// Validate: return false if rentDate or dueDate are not valid
		if (!Helper.isValidDate(rentDate) || !Helper.isValidDate(dueDate)) {
			System.err.println("Rent date or Due date is not valid.");
			return false;
		}

		// TODO: check rentDate is available. Check with prof if need to check
		// conflict interval
		if (rs != null) {
			try {
				if (Helper.timeDifference(rentDate, rs.dueDate) > 0 
						&& Helper.timeDifference(dueDate, rs.rentDate) < 0) {
					System.err.println("Rent time is overlaped.");
					return false;
				}
			} catch (DateFormatException e) {
				return false;
			}
		}

		// Validate: if rentDate > dueDate catch RentPeriodException and return
		// false
		RentSettings rs;
		try {
			rs = this.new RentSettings(rentDate, dueDate, library);
			rs.borrowed = true;
		} catch (RentPeriodException | DateFormatException e) {
			return false;
		}

		this.rs = rs;
		System.out.println(this + " was rent successfully.");
		return true;
	}

	// destroy the RentSettings object for this book
	public void returnBook(Library library) {
		
		// Validate to check if library contains any book
		if (library.getBooks() == null || library.getBooks().size() == 0) {
			System.err.println("Bad request! Library must have atleast one book.");
		}
		
		
		for (int i=0; i<library.getBooks().size(); i++) {
			final Book bk = (Book) library.getBooks().get(i);
			if (bk != null && bookName.equalsIgnoreCase(bk.getBookName())) {
				bk.rs = null;
				library.editBook(i, bk);
			}
		}
	}

	// return the date when this book is available
	public String availableDate(Library library) {
		if (rs == null)
			return Helper.getCurrentDate();
		return rs.dueDate;
	}

	// returns true if the current date is greater than the due date
	public boolean isBookOverdue() {
		if (rs == null) {
			System.err.println("Book rent settings has not been set yet.");
			return false;
		}
		String currentdate = Helper.getCurrentDate();
		try {
			if (Helper.timeDifference(rs.dueDate, currentdate) > 0) {
				return true;
			}
		} catch (DateFormatException e) {
			return false;
		}

		return false;
	}

	// TODO: why pass libary here? Since many lib can have same book.
	// TODO: This function need to be revisit
	public boolean isRented(Library l) {
		// The book has been rented and not yet returned
		for (int i=0; i<l.getBooks().size(); i++) {
			final Book book = (Book) l.getBooks().get(i);
			if (book == null || !book.getBookName().equals(this.getBookName())) {
				continue;
			}
			if (book.rs == null) {
				return false;
			} else {
				if (book.rs.borrowed) {
					return true;
				}
			}
		}
		return false;
	}

	public RentSettings getRs() {
		return rs;
	}

	public void setRs(RentSettings rs) {
		this.rs = rs;
	}

	public String bookName() {
		return "(" + bookName + ", " + valueTag + ')';
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("(").append(bookName).append(", ").append(valueTag);
		if (library != null) {
			sb.append(" => ").append(library.getLibraryName()).append(")");
			if (rs != null) {
				sb.append(rs);
			}
		} else {
			sb.append(")");
		}
		return sb.toString();
	}

	public String getBookName() {
		return bookName;
	}

	public int getValueTag() {
		return valueTag;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	private class RentSettings {

		private String rentDate; // date when the item is requested
		private String dueDate; // date when the item must be returned
		private boolean borrowed = false; // true if the item is rented

		private Library library;

		// default ctr: daily rent
		private RentSettings() throws DateFormatException {
			this.rentDate = Helper.getCurrentDate();
			this.dueDate = Helper.getCurrentDate();
		}

		// private ctr must throw DateFormatException and RentPeriodException
		private RentSettings(String rentDate, String dueDate, Library library)
				throws DateFormatException, RentPeriodException {

			// Validate: if rentDate > dueDate catch RentPeriodException and
			// return false
			if (Helper.timeDifference(rentDate, dueDate) < 0) {
				throw new RentPeriodException("Due date must be after Rent date.");
			}

			this.rentDate = rentDate;
			this.dueDate = dueDate;
			this.library = library;

		}

		@Override
		public String toString() {
			return "RentSettings (" + rentDate + ", " + dueDate + ", " + library.getLibraryName() + ", " + borrowed
					+ ")";
		}
		
		
	}
}
