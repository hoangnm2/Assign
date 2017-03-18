import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		/* TASK 1 - build libraries from files - at least two libraries */

		System.out.println("\n\n *" + " TASK 1 " + "*");
		Libraries ls = new Libraries(2);

		ls.buildLibraryFromFile("Newnham", "NewnhamLibrary.txt");
		ls.buildLibraryFromFile("York", "YorkLibrary.txt");
		System.out.println(ls);

		/* TASK 2 - ask for a book that is not in any library inventory */
		{
			System.out.println("\n\n *" + " TASK 2 " + "*");
			Book book = new Book("C++", 20);
			Library<?> library = ls.isThereBookInLibraries(book);
			if (library == null)
				System.out.println(Helper.printNonexistent(book));
			else
				System.out.println(
						"Book '" + book.getBookName() + "' belongs to library '" + library.getLibraryName() + "'.");
		}

		/*
		 * TASK 3 - ask for a book that is in a library inventory issue a rent
		 * request and print the bookEssentials of Database Management issue the
		 * same rent request and print the book return the book issue the rent
		 * request with new dates and print the book
		 */

		System.out.println("\n\n *" + " TASK 3 " + "*");

		// Ask for a book that is in a library inventory
		Book wantedBook = new Book("Essentials of Database Management", 20);
		Library<Book> library = ls.isThereBookInLibraries(wantedBook);
		if (library == null)
			System.out.println(Helper.printNonexistent(wantedBook));
		else {
			System.out.println(
					"Book '" + wantedBook.getBookName() + "' belongs to library '" + library.getLibraryName() + "'.");

			// Issue a borrow request and print the book object
			library.rentRequest(wantedBook, "03/24/2017", "05/1/2017");

			// Issue the same borrow request and print the book object
			library.rentRequest(wantedBook, "04/2/2017", "05/4/2017");

			// Return the book
			wantedBook.returnBook(library);

			// Issue the borrow request with new dates (TODO: why new dates
			// here? Does it matter?)
			library.rentRequest(wantedBook, "03/9/2017", "03/14/2017");
		}

		/*
		 * TASK 4 - ask for the same book in all libraries if you can find a
		 * library, rent the book from that library
		 */
		System.out.println("\n\n *" + " TASK 4 " + "*");

		{
			final Book bookToRent = new Book("Lambra expression for Java 8", 60);

			final List<Library<?>> isThereBookInLibs = new LinkedList<Library<?>>();

			Library<Book> library1;
			while ((library1 = ls.isThereBookInLibraries(bookToRent)) != null) {
				isThereBookInLibs.add(library1);
				library1.rentRequest(bookToRent, "03/24/2017", "05/1/2017");
			}

			System.out.println("All the libraries that having the book '" + bookToRent.getBookName() + "':");
			for (Library l : isThereBookInLibs) {
				System.out.println(l.getLibraryName());
			}
		}

		System.out.println("--------------------------------------------------------------");
		{
			final Book bookToRent = new Book("SQL Server", 60);
			
			Library<Book> library2;
			final List<Library<?>> availableBookInLibs = new LinkedList<Library<?>>();
			while ((library2 = ls.rentBookAvailable(bookToRent, "5/1/2017", "5/5/2017")) != null) {
				availableBookInLibs.add(library2);
				library2.rentRequest(bookToRent, "5/1/2017", "5/5/2017");
			}
			ls.rentBookAvailable(bookToRent, "5/1/2017", "5/5/2017");
			
			System.out.println(
					"All the libraries where the book '" + bookToRent.getBookName() + "' is available to be borrowed:");
			for (Library lib : availableBookInLibs) {
				System.out.println(lib.getLibraryName());
			}
		}
		System.out.println("--------------------------------------------------------------");

		/* TASK 5 - calculate maximum value tag for each library */
		System.out.println("\n\n *" + " TASK 5 " + "*");
		System.out.println("The greatest value tag of all the books from each library:");
		for (final Library<?> lib : ls.libraries) {
			int maxTagValue = lib.findMaximumValueTag();
			System.out.println(lib.getLibraryName() + ": " + maxTagValue);
		}

		/*
		 * TASK 6 - inquire about a book - it is available? when does it become
		 * available, etc.
		 */
		System.out.println("\n\n *" + " TASK 6 " + "*");
		final Book issuedBook = new Book("Java: The Complete Reference Ninth Edition", 45);
		issuedBook.rentBook("03/09/2017", "03/10/2017", ls.libraries[1]);

		// Is it borrowed?
		boolean isRented = issuedBook.isRented(ls.libraries[1]);
		System.out.println("Is the book rented: " + (isRented ? "yes" : "no"));

		// Is it overdue?
		boolean isOverdue = issuedBook.isBookOverdue();
		System.out.println("Is it overdue: " + (isOverdue ? "yes" : "no"));

		// Could it be found in more than one library?
		// TODO: need to change method signature in Libraries
		// isThereBookInLibraries to return an array of Library

		// When can it be borrowed? TODO: how can know in case of overdue?
		// Someone keep the book for 2 or 3 days more?
		final String avalableTime = issuedBook.availableDate(ls.libraries[1]);
		if (avalableTime != null) {
			System.out.println("Available from: " + avalableTime);
		}
	}
}
